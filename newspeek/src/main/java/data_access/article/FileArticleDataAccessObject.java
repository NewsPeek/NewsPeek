package data_access.article;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import entity.article.Article;
import io.github.cdimascio.dotenv.Dotenv;
import use_case.save_article.SaveArticleDataAccessInterface;

/**
 * DAO to store articles in files.
 */
public class FileArticleDataAccessObject implements SaveArticleDataAccessInterface {
    // The (open) filesystem directory in which the files are stored.
    private final File directory;

    // Cached map from filename to article title. Is null if not generated yet.
    private Map<String, String> titleCache;

    private final Gson gson;

    public FileArticleDataAccessObject() {
        String path = loadPathFromDotenv();
        if (path == null) {
            this.directory = null;
        } else {
            this.directory = new File(path);
        }

        this.titleCache = null;

        /**
         * Adapter to allow serializing LocalDateTime objects with Gson.
         */
        class LocalDateTimeAdapter extends TypeAdapter<java.time.LocalDateTime> {
            @Override
            public void write(JsonWriter writer, LocalDateTime value) throws IOException {
                if (value == null) {
                    writer.nullValue();
                } else {
                    writer.value(value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                }
            }

            @Override
            public LocalDateTime read(JsonReader reader) throws IOException {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return null;
                }
                return java.time.LocalDateTime.parse(reader.nextString());
            }
        }

        gson = new GsonBuilder()
                .registerTypeAdapter(java.time.LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

    private String loadPathFromDotenv() {
        // Automatically loads the .env file
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String loadedPath = dotenv.get("SAVED_ARTICLES_PATH");

        if (loadedPath == null || loadedPath.isEmpty()) {
            // this will show the user an error later if they try to save or load an article
            return null;
        }
        return loadedPath;
    }

    /**
     * Check that the directory given by SAVED_ARTICLES_PATH exists, or create it if it doesn't.
     * Should be called only before accessing the directory, so the app can be used with an invalid directory
     * as long as the relevant buttons aren't pressed.
     * @throws IOException if SAVED_ARTICLES_PATH is not specified, the path points to a non-directory file,
     *                     or the directory can't be created.
     */
    private void validateDirectory() throws IOException {
        if (this.directory == null) {
            throw new IOException("SAVED_ARTICLES_PATH not specified");
        }
        if (!this.directory.exists()) {
            if (!this.directory.mkdirs()) {
                throw new IOException("Could not create saved articles directory " + this.directory.getAbsolutePath());
            }
        }
        if (!this.directory.isDirectory()) {
            throw new IOException(this.directory.getAbsolutePath() + " is not a directory.");
        }
    }

    /**
     * Generates the title cache if it is null. Otherwise, do nothing. Other methods are expected
     * to update the title cache to ensure consistency with the filesystem, so we shouldn't have to
     * generate it again.
     * @throws IOException if there's an issue with the directory.
     */
    private void generateTitleCache() throws IOException {
        validateDirectory();
        if (this.titleCache == null) {
            this.titleCache = new HashMap<>();
            for (final File file : Objects.requireNonNull(this.directory.listFiles())) {
                if (file.isFile() && file.getName().endsWith(".json")) {
                    try {
                        Article article = loadArticle(file);
                        titleCache.put(file.getName(), article.getTitle());
                    } catch (IOException exception) {
                        // File couldn't be read; delete it from storage
                        System.out.println("Deleting invalid saved article " + file.getName()
                                + " due to the following exception: " + exception.getMessage());
                        if (!file.delete()) {
                            throw new IOException("Could not delete invalid saved article " + file.getName());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void saveArticle(Article article) throws IOException {
        validateDirectory();
        generateTitleCache();
        try {
            // Generate a unique id
            String id;
            do {
                // unlikely to ever run more than once, as this would require an uber-rare UUID collision
                id = UUID.randomUUID() + ".json";
            } while (this.titleCache.containsKey(id));

            File saveFile = new File(this.directory, id);

            // Auto-closes after write
            try (FileWriter fileWriter = new FileWriter(saveFile)) {
                fileWriter.write(gson.toJson(article));
            }

            // Only add to title cache after file writing so that if the save fails, the article
            // won't be added.
            this.titleCache.put(id, article.getTitle());
        } catch (JsonIOException exception) {
            throw new IOException(exception);
        }
    }

    /**
     * Loads the article from the given article ID.
     * @param id the unique article ID. This should be obtained from an earlier call to listSavedArticles().
     * @return the article with the given ID.
     * @throws IOException if the article can't be found, or there's an issue with the directory.
     */
    public Article loadArticle(String id) throws IOException {
        validateDirectory();
        for (final File file : Objects.requireNonNull(this.directory.listFiles())) {
            if (file.isFile() && file.getName().equals(id)) {
                return loadArticle(file);
            }
        }
        throw new IOException(this.directory.getAbsolutePath() + "/" + id + " not found or not a file.");
    }

    /**
     * Loads the article from the given article file.
     * @param file the File object containing the article in JSON format.
     * @return the article from the given File.
     * @throws IOException if the article can't be loaded.
     */
    private Article loadArticle(File file) throws IOException {
        JsonReader reader = new JsonReader(new FileReader(file));
        try {
            Article article = gson.fromJson(reader, Article.class);
            if (article == null) {
                throw new IOException("Article " + file.getName() + " is null.");
            }
            return article;
        } catch (JsonIOException | JsonSyntaxException exception) {
            throw new IOException(exception);
        }
    }

    /**
     * Returns a mapping from article ID to article title.
     * An article ID is a unique string that represents an article in the database.
     * This call may take longer the first time.
     * @return a mapping from article ID to article title.
     * @throws IOException if there's an issue with the directory.
     */
    public Map<String, String> listSavedArticles() throws IOException {
        generateTitleCache();
        return titleCache;
    }
}
