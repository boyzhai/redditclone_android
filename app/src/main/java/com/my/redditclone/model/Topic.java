
package com.my.redditclone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Topic implements Comparable<Topic>{

    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("created_date")
    private String createdDate;
    @SerializedName("updated_date")
    private String updatedDate;
    @SerializedName("up_voted_count")
    private Integer upVotedCount;
    @SerializedName("down_voted_count")
    private Integer downVotedCount;

    private boolean addTopic;

    public Topic() {
    }

    public Topic(boolean addTopic) {
        this.addTopic = addTopic;
    }

    public boolean isAddTopic() {
        return addTopic;
    }
    public void setAddTopic(boolean addTopic) {
        this.addTopic = addTopic;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getUpVotedCount() {
        return upVotedCount;
    }

    public void setUpVotedCount(Integer upVotedCount) {
        this.upVotedCount = upVotedCount;
    }

    public Integer getDownVotedCount() {
        return downVotedCount;
    }

    public void setDownVotedCount(Integer downVotedCount) {
        this.downVotedCount = downVotedCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("title", title).append("description", description).append("createdDate", createdDate).append("updatedDate", updatedDate).append("upVotedCount", upVotedCount).append("downVotedCount", downVotedCount).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(createdDate).append(description).append(id).append(updatedDate).append(title).append(upVotedCount).append(downVotedCount).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Topic) == false) {
            return false;
        }
        Topic rhs = ((Topic) other);
        return new EqualsBuilder().append(createdDate, rhs.createdDate).append(description, rhs.description).append(id, rhs.id).append(updatedDate, rhs.updatedDate).append(title, rhs.title).append(upVotedCount, rhs.upVotedCount).append(downVotedCount, rhs.downVotedCount).isEquals();
    }

    @Override
    public int compareTo(Topic o) {
        if(getUpVotedCount() == null || o.getUpVotedCount() == null){
            return 0;
        }
            return getUpVotedCount().compareTo(o.getUpVotedCount());
    }
}