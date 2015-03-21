package models;

import java.util.Date;

import play.data.validation.Constraints.Required;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.data.format.*;

@Entity
@Table(name="items")
public class Item extends Model{
    @Id
    @GeneratedValue
    public Long id;
    
    @Required
    public String userName;
    
    public String email;
    
    public String description;
    
    public String imageUrl;
    
    @Formats.DateTime(pattern="yyyy-MM-dd hh:mm")
    public Date date = new Date();
    
    public static Finder<Long, Item> find = new Finder<Long, Item>(Long.class, Item.class);
}
