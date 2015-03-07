package models;

import play.data.validation.Constraints.Required;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
@Table(name="items")
public class Item extends Model{
    @Id
    @GeneratedValue
    public Long id;
    
    @Required
    public String name;
    
    //@Required
    public Double price;
    
    //@Required
    public String imageUrl;
    
    public static Finder<Long, Item> find = new Finder<Long, Item>(Long.class, Item.class);
}
