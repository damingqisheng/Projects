package model;

import javafx.scene.image.Image;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * the photo class
 */
public class Photo implements Serializable {
    /**
     * the photo's path
     */
    private String filePath;
    /**
     * the photo's date
     */
    private Date date;
    /**
     * the title
     */
    private String title;
    /**
     * the string of the date
     */
    private String dateStr;
    /**
     * the tags
     */
    private String tags;
    /**
     * the kay value map
     */
    Map<String, List<String>> map=new HashMap<>();

    /**
     * construction
     * @param path the path
     * @param title the title
     */
    public Photo(String path, String title) {
        this.map = new HashMap<String, List<String>>();
        this.filePath = path;
        this.title = title;
        File file = new File(path);
        Calendar cal = Calendar.getInstance();
        long time = file.lastModified();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.setTimeInMillis(time);

        this.date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.dateStr = df.format(this.date);
        this.tags = " ";
    }

    /**
     * update the teg string
     */
    private void updateTags() {
        this.tags = "";
        for(Map.Entry<String, List<String>> entry : map.entrySet()){
            String mapKey = entry.getKey();
            List<String> mapValue = entry.getValue();
            tags += mapKey+"-";
            for (String s:mapValue) {
                tags += s+" ";
            }
            tags+=";";
        }
    }

    /**
     * add tag
     * @param key the key
     * @param value the value
     */
    public void addTag(String key, String value) {
        List<String> values;
        if(map.containsKey(key)){
            values = map.get(key);
            values.add(value);

        } else {
            values = new ArrayList<String>();
            values.add(value);
        }
        map.put(key, values);
        updateTags();
    }
    /**
     * remove tag
     * @param key the key
     * @param value the value
     */
    public void rmTag(String key, String value) {
        List<String> values;
        if(map.containsKey(key)){
            values = map.get(key);
            if(values.contains(value)){
                values.remove(value);
                if (values.size() == 0){
                    map.remove(key);
                }else {
                    map.put(key, values);
                }
                updateTags();
            }
        }
    }

    /**
     * check if the photo contains the tag
     * @param kv the tag
     * @return true for contain, otherwise false
     */
    public boolean containsTag(String kv){
        String a[] = kv.split("7788");
        if (map.containsKey(a[0])){
            if(map.get(a[0]).contains(a[1])){
                return true;
            }
        }
        return false;
    }


    /**
     * get the image of the photo
     * @return the image
     */
    public Image getImage() {
        File file = new File(filePath);
        try {
            return new Image(file.toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * return the date
     * @return the date
     */
    public Date getDate() {
        return date;
    }


    /**
     * return the title
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * add title
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * return the date string
     * @return the string
     */
    public String getDateStr() {
        return dateStr;
    }


    /**
     * return tags
     * @return the tags
     */
    public String getTags() {
        return tags;
    }


    /**
     * return the path
     * @return the path
     */
    public String getFilePath() {
        return filePath;
    }


}
