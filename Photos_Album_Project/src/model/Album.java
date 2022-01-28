package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * the album class
 */
public class Album  implements Serializable {
    /**
     * the list of photos
     */
    private List<Photo> photos;
    /**
     * the num of photos in the album
     */
    private int num;
    /**
     * the number string
     */
    private String numStr;
    /**
     * the date range
     */
    private String dateRange;
    /**
     * the name of the album
     */
    private String name;
    /**
     * the fisrt date and end date of the album
     */
    private Date first, end;

    /**
     * construction
     * @param albumName the name
     */
    public Album(String albumName) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.photos = new ArrayList<Photo>();
        this.num = 0;
        this.numStr = "0";
        this.dateRange = " - ";
        this.name = albumName;

        try {
            this.first = df.parse("5000-10-10 10:10:10");
            this.end = df.parse("100-00-00 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * update date range of the album
     */
    public void updateDateRange(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.first = df.parse("5000-10-10 10:10:10");
            this.end = df.parse("100-00-00 00:00:00");
            for(Photo p : photos) {
                if (p.getDate().after(end)){
                    this.end = p.getDate();
                }
                if (p.getDate().before(this.first)){
                    this.first = p.getDate();
                }
            }
            this.dateRange = df.format(this.first)+"-"+df.format(this.end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * add photo
     * @param p the photo
     */
    public void addPhoto(Photo p) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (p.getDate().after(end)){
            this.end = p.getDate();
        }
        if (p.getDate().before(this.first)){
            this.first = p.getDate();
        }
        this.dateRange = df.format(this.first)+"-"+df.format(this.end);
        this.photos.add(p);
        this.num ++;
    }

    /**
     * remove photo
     * @param path the path
     */
    public void rmPhoto(String path) {
        for(int i=0; i<photos.size(); i++) {
            if(photos.get(i).getFilePath().equals(path)) {
                photos.remove(i);
                this.num --;
                break;
            }
        }
    }

    /**
     * change a photo's title
     * @param path the path
     * @param title the title
     */
    public void changePhotoTile(String path, String title) {
        for(int i=0; i<photos.size(); i++) {
            if(photos.get(i).getFilePath().equals(path)) {
                photos.get(i).setTitle(title);
                break;
            }
        }
    }

    /**
     * add tag to photo
     * @param path the path
     * @param key the tag's kay
     * @param value the tag's value
     */
    public void addTagToPhoto(String path, String key, String value) {
        for(int i=0; i<photos.size(); i++) {
            if(photos.get(i).getFilePath().equals(path)) {
                photos.get(i).addTag(key, value);
                break;
            }
        }
    }

    /**
     * remove tag from photo
     * @param path the path
     * @param key the tag's kay
     * @param value the tag's value
     */
    public void rmTagFromPhoto(String path, String key, String value) {
        for(int i=0; i<photos.size(); i++) {
            if(photos.get(i).getFilePath().equals(path)) {
                photos.get(i).rmTag(key, value);
                break;
            }
        }
    }

    /**
     * find photo
     * @param path the path
     * @return the photo
     */
    public Photo findPhoto(String path) {
        for(int i=0; i<photos.size(); i++) {
            if(photos.get(i).getFilePath().equals(path)) {
                return photos.get(i);
            }
        }
        return null;
    }

    /**
     * search photo
     * @param date1 the start date
     * @param date2 the end date
     * @param kV1 the tag1
     * @param kV2 the tag2
     * @param andOR 'and' or 'or'
     * @return a list of photos
     */
    public List<Photo> filterTagHandler(Date date1, Date date2, String kV1, String kV2, String andOR) {
        List<Photo> filters = new ArrayList<Photo>();

        for(Photo p: photos) {
            if(date1 != null && date2 != null && p.getDate().after(date1) && p.getDate().before(date2)) {
                if (!kV1.equals("")) {
                    if(!kV2.equals("")) {
                        if(andOR.equals("and") && p.containsTag(kV1) && p.containsTag(kV2)){
                            filters.add(p);
                        } else if(andOR.equals("or") && (p.containsTag(kV1) || p.containsTag(kV2))) {
                            filters.add(p);
                        }
                    } else {
                        if(p.containsTag(kV1)){
                            filters.add(p);
                        }
                    }
                } else if (!kV2.equals("")){
                    if(p.containsTag(kV2)){
                        filters.add(p);
                    }
                } else {
                    filters.add(p);
                }
            } else if (date1 == null && date2 == null) {
                if (!kV1.equals("")) {
                    if(!kV2.equals("")) {
                        if(andOR.equals("and") && p.containsTag(kV1) && p.containsTag(kV2)){
                            filters.add(p);
                        } else if(andOR.equals("or") && (p.containsTag(kV1) || p.containsTag(kV2))) {
                            filters.add(p);
                        }
                    } else {
                        if(p.containsTag(kV1)){
                            filters.add(p);
                        }
                    }
                } else if (!kV2.equals("")){
                    if(p.containsTag(kV2)){
                        filters.add(p);
                    }
                } else {
                    filters.add(p);
                }
            }
        }

        return filters;
    }

    /**
     * get number
     * @return the nu,ber
     */
    public int getNum() {
        return num;
    }

    /**
     * set the number
     * @param num the number
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * get date range
     * @return the range
     */
    public String getDateRange() {
        return dateRange;
    }

    /**
     * set range
     * @param dateRange the range
     */
    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    /**
     * return the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * set name
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get number
     * @return the number
     */
    public String getNumStr() {
        return ""+num;
    }

    /**
     * set number
     * @param numStr the number
     */
    public void setNumStr(String numStr) {
        this.numStr = numStr;
        this.num = Integer.parseInt(this.numStr);
    }

    /**
     * get photos
     * @return the photos
     */
    public List<Photo> getPhotos() {
        return photos;
    }

    /**
     * set photos
     * @param photos the photos
     */
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }


}
