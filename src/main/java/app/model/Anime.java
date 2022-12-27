package app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Anime {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("russian")
    @Expose
    private String russian;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("status")
    @Expose
    private String status;

    private int episodes;


    @Override
    public String toString() {
        return "Anime{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", russian='" + russian + '\'' +
                ", kind='" + kind + '\'' +
                ", score='" + score + '\'' +
                ", status='" + status + '\'' +
                ", episodes=" + episodes +
                '}';
    }
}
