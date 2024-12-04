# NewsPeek

**News censorship system (satire)**

Written by **Tyson Caul, Arnav Chandna, Ray Chen, Jettae Schroff**, and **Liza Tsoy**.

## Table of contents
**Features**

**Installation**

**Usage**

**License**

**Contributing**

## Features

- Up-to-date world news
- Censor articles from any website
- Save articles and read them later (persistent database)
- Import custom censorship rulesets from JSON files
- Temporarily disable censorship
- Summary of censored words

## Installation

*Precompiled JAR files are not currently available.*

### IntelliJ (Windows, macOS, or Linux)

1. Install [IntelliJ Idea](https://www.jetbrains.com/idea/download/).
1. Open IntelliJ.
1. Choose *New Project from Version Control* and enter the URL `https://github.com/newspeek/newspeek`.
1. Set up `.env` (see **Setting up `.env`** below).

### Command line (macOS and Linux)

*Some experience using the command line is recommended.*

1. Open a terminal.
1. Install [Maven](https://maven.apache.org/install.html) build system.
1. Install [Java 17](https://www.oracle.com/java/technologies/downloads/).
1. Clone the repository with `git clone https://github.com/newspeek/newspeek`.
1. Move into the directory with `cd newspeek`.
1. Set up `.env` (see **Setting up `.env`** below).
1. Build the program with `mvn -B package --file pom.xml`.

### Setting up `.env`

In the root directory of the project, which appears as `newspeek-parent` in IntelliJ,
create a new text file called `.env`.

If you would like to use the **Random Article** button:

1. Sign up for [NewsAPI](https://newsapi.org/register).
1. Find your API key on the [account page](https://newsapi.org/account).
1. Add the line `NEWS_API_KEY=your-api-key-here` to the `.env` file, replacing `your-api-key-here` with your API key.
Do not surround your API key with quotes; paste it exactly as-is to the right of the `=`.

If you would like to use the article save system:

1. Choose a location to store your saved articles.
The folder doesn't necessarily have to exist, but it must be somewhere your user can create files.
1. Find the full path of your chosen location.
   - On Windows, this might be something like `C:\Users\orwell\Documents\NewsPeekSaves`.
   - On macOS, this might be something like `/Users/orwell/Documents/NewsPeekSaves`.
   - On Linux, this might be something like `/home/orwell/Documents/NewsPeekSaves`.
1. Add the line `SAVED_ARTICLES_PATH=/your/path/here` to the `.env` file, replacing `/your/path/here` with the full path.
      Do not surround the path with quotes; paste it exactly as-is to the right of the `=`.

Save the file when you're done (IntelliJ will do this automatically) and continue with the installation process.

## Usage

### Running the app

#### IntelliJ

1. Open the topmost folder (called **newspeek-parent**).
1. Right-click on the `pom.xml` file inside, then click *Maven > Reload Project*.
1. Double-click on *newspeek > src > main > java > app > Main.java* in the file tree.
1. Click the run button (green triangle) to the left of the line `public class Main {`.

#### Command Line

Run the executable with `java -jar newspeek/target/newspeek-1.0-SNAPSHOT-jar-with-dependencies.jar`.

### Getting an article to read

Before doing anything else, you must open an article.
If you have set up your NewsAPI key in the `.env`, try clicking **Random Article**.
If you'd rather see a specific news article, click **Load from URL** and enter the URL of a news article in the pop-up.
The article should appear in the yellow box on the left of the screen.

### Saving the article

If you've set up your save location in `.env`, click **Save Article**.
You should see a confirmation box with the text *Saved article successfully.*

### Loading a saved article

Once you've saved some articles, you can load them to read them again, even if you've closed the app since saving them.

Click **Load Article** and a dropdown menu will appear below the button.
Note that this could take a moment if you've saved many articles.

Click on the menu and then click on a saved article. The article to the left will change immediately.

### Changing the censorship rules

To change the censorship rules in effect, click **Load Ruleset** and choose a ruleset file from the file picker.
There are some default rulesets in the folder *JsSONDataRuleSets* in the project folder;
navigate here in the file picker to load one of them.

You can also create your own censorship ruleset file in JSON format with the following template:
```json
{
  "ruleSetName": "Example Ruleset",
  "caseSensitive": false,
  "censoredWords": [
    {
      "original": "freedom",
      "censored": "permitted choices"
    },
    {
      "original": "protest",
      "censored": "public indecency"
    },
    . . .
  ],
  "prohibitedWords": [
    "revolution",
    "dictator",
    . . .
  ]
}
```

#### Explanation

`ruleSetName` (optional): a name to be displayed when loading the ruleset. Can be any string.

`caseSensitive` (optional): if set to `true`, we will only censor words that match the case exactly.
For example, if `Trump` is `prohibitedWords`, `Donald Trump` will become `Donald xxxxx` but `trump card` won't be censored.

`censoredWords`: a list of mappings from an original word to a censored word that takes its place. This can be empty.

`prohibitedWords`: a list of words to censor. They will be replaced with a string of `xxxx...` in the article.

### Uncensoring the article

Press the `U` key on your keyboard to temporarily disable censorship.
The text *Uncensored* will be appear and the article will be displayed without censorship.

Press `U` again to re-enable censorship.
The text *Uncensored* will disappear and the censorship will be restored.

## License

This software is licensed under the MIT License. The full text is below:

```
MIT License

Copyright (c) 2024 Tyson Caul, Arnav Chandna, Ray Chen, Jettae Schroff, and Liza Tsoy

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## Contributing

### Reporting bugs

Create an issue on GitHub and add the `bug` label to it. Clearly explain:

- what buttons to press to cause the bug
- what you expect to happen
- what actually happens
- your `.env` configuration
- your operating system

### Contributing

To contribute to this project, create a fork on GitHub.
Make your changes in your fork, then create a pull request to the original repository trying to merge into `main`.
In the comments, clearly explain what this pull request does and why we should merge it.
Your pull request must:
- have 100% test coverage, as reported by the GitHub Actions bot
- pass all tests, as reported by the GitHub checks