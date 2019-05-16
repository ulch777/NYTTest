package ua.ulch.nyttest.model;

import java.io.Serializable;
import java.util.Arrays;

public class Response implements Serializable {
    private String copyright;

    private Results[] results;

    private String num_results;

    private String status;

    public String getCopyright ()
    {
        return copyright;
    }

    public void setCopyright (String copyright)
    {
        this.copyright = copyright;
    }

    public Results[] getResults ()
    {
        return results;
    }

    public void setResults (Results[] results)
    {
        this.results = results;
    }

    public String getNum_results ()
    {
        return num_results;
    }

    public void setNum_results (String num_results)
    {
        this.num_results = num_results;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Response{" +
                "copyright='" + copyright + '\'' +
                ", results=" + Arrays.toString(results) +
                ", num_results='" + num_results + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
