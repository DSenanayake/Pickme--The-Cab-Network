package Model;

public class Task {

    private String page_url = "";

    public Task(String page_url) {
        this.page_url = page_url;
    }

    /**
     * @return the page_url
     */
    public String getPage_url() {
        return page_url;
    }

    /**
     * @param page_url the page_url to set
     */
    public void setPage_url(String page_url) {
        this.page_url = page_url;
    }

}
