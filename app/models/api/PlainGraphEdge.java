package models.api;

public interface PlainGraphEdge {
    public String getIndex();
    public String getSource();
    public String getTarget();
    public String getLabel();
    public Double getValue();
}
