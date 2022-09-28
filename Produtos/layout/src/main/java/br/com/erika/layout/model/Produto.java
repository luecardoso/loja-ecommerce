package br.com.erika.layout.model;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Produto {

    public Produto(Long id, String name, Date date){
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public Produto(){
        
    }

    private Long id;
    private String name;

    @DateTimeFormat(pattern = "dd/MM/YYYY HH:mm:ss")
    private Date date;

    /**
     * @param id
     */
    public void setId(Long id){
        this.id = id;
    }  
    
    public Long getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Date getDate(){
        return date;
    }
}
