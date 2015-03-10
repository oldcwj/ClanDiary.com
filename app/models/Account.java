package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@Table(name="account")
public class Account extends Model{
    @Id
    @GeneratedValue
    public Long id;
    
    @Required
    public String userName;
    
    @Required
    public String password;
    
    public int type;
    
    @Formats.DateTime(pattern="yyyy-MM-dd hh:mm")
    public Date createDate;  //创建账户时间
    
    public static Finder<Long, Account> find = new Finder<Long, Account>(Long.class, Account.class);
}
