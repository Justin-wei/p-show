package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

import java.io.Serializable;

/**
 * Created by Justin-pc on 2017/3/7.
 */
public class Article implements Serializable {
    @JsonProperty("_id")
    public ObjectId id;
    public String articleName;
    public String publishedDate;
    public String tag;
    public String articleIntroduction;
    public String articleText;
    public String articleHtml;
}
