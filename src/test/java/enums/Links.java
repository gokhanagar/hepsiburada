package enums;

public enum Links {
    BASEURL("https://www.hepsiburada.com/");

    private String getLink;

    Links(String getLink) {
        this.getLink = getLink;
    }

    public String getLink() {
        return getLink;
    }

}