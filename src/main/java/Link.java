import java.math.BigDecimal;
import java.util.Objects;

public class Link {
    private String url;
    private BigDecimal hubbiness;
    private BigDecimal authority;

    public Link(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(url, link.url);
    }

    @Override
    public String toString() {
        return "Link{" +
                "url='" + url + '\'' +
                ", hubbiness=" + hubbiness +
                ", authority=" + authority +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigDecimal getHubbiness() {
        return hubbiness;
    }

    public void setHubbiness(BigDecimal hubbiness) {
        this.hubbiness = hubbiness;
    }

    public BigDecimal getAuthority() {
        return authority;
    }

    public void setAuthority(BigDecimal authority) {
        this.authority = authority;
    }
}
