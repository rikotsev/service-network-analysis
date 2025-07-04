package analysis.example;

public class Relationship {

    private String src;
    private String dst;
    private String relationship;

    public Relationship() {}

    public Relationship(String src, String dest, String relationship) {
        this.src = src;
        this.dst = dest;
        this.relationship = relationship;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
