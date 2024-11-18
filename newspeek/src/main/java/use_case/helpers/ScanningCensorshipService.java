package use_case.helpers;

import entity.article.Article;
import entity.censorship_rule_set.CensorshipRuleSet;

public class ScanningCensorshipService implements CensorshipService {

    private static final String WORD_REGEX = "[ -.,()\\[\\]{};:\"']+";
    private static final String PUNCTUATION_REGEX = "[^ -.,()\\[\\]{};:\"']+";
    @Override
    public Article censor(Article article, CensorshipRuleSet ruleset) {
        // loop through the words in the article.getText() and apply censorship rules
        Article result = article.copy();
        StringBuilder censoredText = new StringBuilder();

        String[] articleWords = result.getText().split(WORD_REGEX);
        String[] articlePunctuation = result.getText().split(PUNCTUATION_REGEX);


        // Input validation
        if(invalidInput(articleWords, articlePunctuation, result)) {
            return result;
        }


        int wordAdjustment = 0;

        if(articleWords[0].isEmpty()) {
            wordAdjustment = 1;
        }


        for(int i = 0; i < articleWords.length - wordAdjustment; i++){
            String word = articleWords[i + wordAdjustment];

            censoredText.append(articlePunctuation[i]);



            censoredText.append(getCensored(word, ruleset));
        }

        endAdjustment(articleWords, articlePunctuation, censoredText, wordAdjustment);

        result.setText(censoredText.toString());
        return result;
    }

    private String getCensored (String word, CensorshipRuleSet ruleset) {
        String searchWord;
        if (!ruleset.isCaseSensitive()) {
            searchWord = word.toLowerCase();
        } else {
            searchWord = word;
        }
        if (ruleset.getProhibitions().contains(searchWord)) {
            return censorWord(word);
        } else {
            return ruleset.getReplacements().getOrDefault(searchWord, word);
        }
    }



    /* Detects if words and punctuation are in edge cases.
     * If it is in one of these edge cases, it will edit result to match
     *
     */
    private boolean invalidInput(String[] words, String[] punctuation, Article result){
        StringBuilder resultText = new StringBuilder();
        if(words.length == 0) {
            resultText.append(punctuation[0]);
            result.setText(resultText.toString());
            return true;
        }
        else if (punctuation.length == 0){
            resultText.append(words[0]);
            result.setText(resultText.toString());
            return true;
        }
        else return words[0].isEmpty() && punctuation[0].isEmpty();
    }

    private void endAdjustment(String[] articleWords, String[] articlePunctuation,
                               StringBuilder censoredText, int wordAdjustment) {
        if(wordAdjustment == 1 | articleWords.length < articlePunctuation.length) {
            censoredText.append(articlePunctuation[articlePunctuation.length-1]);
        }
    }

    private String censorWord(String word) {
        return new String(new char[word.length()]).replace("\0", "x");
    }
}