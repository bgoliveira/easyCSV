package me.brunogomes.easyCSV;

/**
 *
 */
public enum FileDelimiter
{
    COMA(","),
    COLUMN(":"),
    SEMI_COLUMN(";"),
    PIPE("|");

    private String delimiterCharacter;

    FileDelimiter(final String delimiterCharacter)
    {
        this.delimiterCharacter = delimiterCharacter;
    }

    public String getDelimiterCharacter()
    {
        return this.delimiterCharacter;
    }
}