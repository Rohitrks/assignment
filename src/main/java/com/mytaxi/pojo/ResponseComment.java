/*-----------------------------------com.mytaxi.pojo.ResponseComment.java-----------------------------------*/

package com.mytaxi.pojo;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"id",
"body",
"postId"
})
public class ResponseComment {

@JsonProperty("id")
private Integer id;
@JsonProperty("body")
private String body;
@JsonProperty("postId")
private Integer postId;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("id")
public Integer getId() {
return id;
}

@JsonProperty("id")
public void setId(Integer id) {
this.id = id;
}

@JsonProperty("body")
public String getBody() {
return body;
}

@JsonProperty("body")
public void setBody(String body) {
this.body = body;
}

@JsonProperty("postId")
public Integer getPostId() {
return postId;
}

@JsonProperty("postId")
public void setPostId(Integer postId) {
this.postId = postId;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}