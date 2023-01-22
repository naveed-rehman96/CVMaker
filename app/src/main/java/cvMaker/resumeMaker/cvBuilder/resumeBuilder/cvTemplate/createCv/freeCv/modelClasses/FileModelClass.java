package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

public class FileModelClass {

    String name , location ;
    Drawable itemIcon;
    Boolean isSelected = false;

    public FileModelClass() {
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }



    @Override
    public String toString() {
        return "FileModelClass{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", itemIcon=" + itemIcon +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Drawable getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(Drawable itemIcon) {
        this.itemIcon = itemIcon;
    }
}
