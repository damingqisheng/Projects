package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * the album list class
 */
public class UserAlbums implements Serializable {
    /**
     * the list.
     */
    private List<Album> _albums;

    /**
     * construction.
     */
    public UserAlbums() {
        _albums = new ArrayList<Album>();
    }

    /**
     * add an album to the list.
     * @param s the album
     */
    public void addAlbum(Album s) {
        if (!_albums.contains(s)) {
            _albums.add(s);
        }
    }

    /**
     * remove an album from the list.
     * @param s the album's name
     */
    public void removeAlbum(String s) {
        for (int i = 0; i<_albums.size(); i++){
            if (_albums.get(i).getName().equals(s)){
                _albums.remove(i);
            }
        }
    }

    /**
     * change the album's name
     * @param oldName old name
     * @param newName new name
     */
    public void changeName(String oldName, String newName){
        for (int i = 0; i<_albums.size(); i++){
            if (_albums.get(i).getName().equals(oldName)){
                _albums.get(i).setName(newName);
            }
        }
    }

    /**
     * return the album list.
     * @return the list
     */
    public List<Album> getAlbums() {
        return _albums;
    }

    /**
     * return the names of all albums
     * @return the names
     */
    public List<String> getAlbumNames() {
        List<String> albumNames = new ArrayList<String>();
        for (Album a : _albums) {
            albumNames.add(a.getName());
        }
        return albumNames;
    }

    /**
     * get the photos in an album
     * @param album the album
     * @return the photo list in the album
     */
    public List<Photo> getPhotos(String album){
        for (Album a : this._albums){
            if (a.getName().equals(album)){
                a.updateDateRange();
                return a.getPhotos();
            }
        }
        return new ArrayList<Photo>();
    }

    /**
     * add a photo the an album
     * @param albumName the album's name
     * @param photoPath the photo's path
     * @param title the photo's path
     */
    public void addPhoto(String albumName, String photoPath, String title) {
        for (Album a : this._albums){
            if (a.getName().equals(albumName)){
                a.addPhoto(new Photo(photoPath, title));
                break;
            }
        }
    }

    /**
     * remove a photo from an album
     * @param albumName the album's name
     * @param photoPath the photo's path
     */
    public void rmPhoto(String albumName, String photoPath) {
        for (Album a : this._albums){
            if (a.getName().equals(albumName)){
                a.rmPhoto(photoPath);
                break;
            }
        }
    }

    /**
     * change a photo's title in an ablum
     * @param albumName the album's name
     * @param photoPath the photo's path
     * @param title the new title
     */
    public void changePhotoTitle(String albumName, String photoPath, String title) {
        for (Album a : this._albums){
            if (a.getName().equals(albumName)){
                a.changePhotoTile(photoPath, title);
                break;
            }
        }
    }

    /**
     * add tag to a photo in an album
     * @param albumName tha album's name
     * @param photoPath the photo's path
     * @param key the tag's kay
     * @param value the tag's value
     */
    public void addTagToPhoto(String albumName, String photoPath, String key, String value) {
        for (Album a : this._albums){
            if (a.getName().equals(albumName)){
                a.addTagToPhoto(photoPath, key, value);
                break;
            }
        }
    }

    /**
     * remove tag from a photo in an album
     * @param albumName tha album's name
     * @param photoPath the photo's path
     * @param key the tag's kay
     * @param value the tag's value
     */
    public void rmTagFromPhoto(String albumName, String photoPath, String key, String value) {
        for (Album a : this._albums){
            if (a.getName().equals(albumName)){
                a.rmTagFromPhoto(photoPath, key, value);
                break;
            }
        }
    }

    /**
     * search photo
     * @param albumName tha album's name
     * @param date1 the start date
     * @param date2 the end date
     * @param kV1 the tag1
     * @param kV2 the tag2
     * @param andOR 'and' or 'or'
     * @return a list of photos
     */
    public List<Photo> filterTagHandler(String albumName, Date date1, Date date2, String kV1, String kV2, String andOR) {
        for (Album a : this._albums){
            if (a.getName().equals(albumName)){
                return a.filterTagHandler(date1,date2,kV1,kV2,andOR);
            }
        }
        return new ArrayList<Photo>();
    }

    /**
     * copy photo from one album to another
     * @param fromAlbum source album
     * @param toAlbum dest album
     * @param path the photo's path
     * @return copy success or not
     */
    public boolean copyPhoto(String fromAlbum, String toAlbum, String path) {
        Photo p = null;
        for (Album a : this._albums){
            if (a.getName().equals(fromAlbum)){
                p = a.findPhoto(path);
                if(p == null)
                    return false;
                break;
            }
        }
        for (Album a : this._albums){
            if (a.getName().equals(toAlbum)){
                if(a.findPhoto(path) != null)
                    return false;
                a.addPhoto(p);
                break;
            }
        }
        return true;
    }

    /**
     * update all album's date range
     */
    public void updateDateRange() {
        for (Album a : this._albums) {
            a.updateDateRange();
        }
    }

    /**
     * copy photo from one album to another
     * @param fromAlbum source album
     * @param toAlbum dest album
     * @param path the photo's path
     * @return copy success or not
     */
    public boolean movePhoto(String fromAlbum, String toAlbum, String path) {
        Photo p = null;
        // check receiver
        for (Album a : this._albums){
            if (a.getName().equals(toAlbum)){
                if(a.findPhoto(path) != null)
                    return false;
                break;
            }
        }

        for (Album a : this._albums){
            if (a.getName().equals(fromAlbum)){
                p = a.findPhoto(path);
                if(p == null)
                    return false;
                a.rmPhoto(path);
                break;
            }
        }
        for (Album a : this._albums){
            if (a.getName().equals(toAlbum)){
                if(a.findPhoto(path) != null)
                    return false;
                a.addPhoto(p);
                break;
            }
        }
        return true;
    }
}
