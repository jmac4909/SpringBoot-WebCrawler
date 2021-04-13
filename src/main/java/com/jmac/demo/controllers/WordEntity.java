package com.jmac.demo.controllers;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String word;
    private String site;
    private Long count;
    protected WordEntity() { }

    public WordEntity(final String word, final String site, final long count) {
        this.word = word;
        this.site = site;
        this.count = count;
    }
    @Override
    public final String toString() {
        return String.format("Word[id=%d, word='%s', site='%s', count='%d']", id, word, site, count);
    }
    public final Long getId() {
        return id;
    }
    public final String getWord() {
        return word;
    }
    public final String getSite() {
      return site;
    }
    public final Long getCount() {
        return count;
    }
}
