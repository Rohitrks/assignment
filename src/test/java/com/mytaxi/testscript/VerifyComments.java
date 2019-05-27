package com.mytaxi.testscript;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mytaxi.pojo.ResponseComment;
import com.mytaxi.pojo.ResponsePost;
import com.mytaxi.pojo.ResponseProfile;
import com.mytaxi.utilities.RestApiActions;

public class VerifyComments extends RestApiActions {
	private static Logger log = Logger.getLogger(VerifyComments.class);
	private String uri=getValueForKey("end_point");
	private String uriProfiles=uri+getValueForKey("pathProfiles");
	private String uriPosts=uri+getValueForKey("pathPosts");
	private String uriComments=uri+getValueForKey("pathComments");
	private String userName=getValueForKey("testUserName");
	private int userId=0;
	private String profilesResponse="";
	private String postsResponse="";
	private String commentResponse="";
	List<Integer> listPostId=new ArrayList<>();
	Set<String> setOfComments=new HashSet<>();
	ObjectMapper mapper = new ObjectMapper();

	/*
	 * This is will verify if user exists for the given username and get the userId for the user.
	 */
	@Test(description="verify user is available")
	public void verifyUsersAvailability(){
		profilesResponse=getGETResponse(uriProfiles);

		try {
			ResponseProfile[] responseProfile = mapper.readValue(profilesResponse, ResponseProfile[].class);
			for(int i=0;i<responseProfile.length;i++){
				if(responseProfile[i].getName().equalsIgnoreCase(userName)){
					userId=responseProfile[i].getUserid();
					break;
				}	
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Assert.assertNotEquals(0, userId, "No user found with Username= "+userName);
	}



	/*
	 * Verify if user has some posts.
	 */
	@Test(description="verify if user has posts",dependsOnMethods="verifyUsersAvailability")
	public void verifyPosts(){
		postsResponse = getGETResponse(uriPosts);

		try {
			ResponsePost[] responsePost = mapper.readValue(postsResponse, ResponsePost[].class);
			for(int i=0;i<responsePost.length;i++){
				if(responsePost[i].getUserid()==userId){
					listPostId.add(responsePost[i].getId());
				}
			} 
		}
		catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(listPostId);
		Assert.assertNotEquals(0, listPostId.size(), "No Posts Found for the user with userId = "+userId);
	}

	/*
	 * verify that Posts has comments and create a set of comments
	 */
	@Test(description="verify Posts has Comments",dependsOnMethods="verifyPosts")
	public void verifyComments(){
		commentResponse = getGETResponse(uriComments);
		try {
			ResponseComment[] responseComment = mapper.readValue(commentResponse, ResponseComment[].class);
			for(int i=0;i<responseComment.length;i++){
				if(listPostId.contains(responseComment[i].getPostId())){
					setOfComments.add(responseComment[i].getBody());
				}
			} 
		}
		catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info(setOfComments);
		Assert.assertNotEquals(0, setOfComments.size(), "No Comments Found on posts made by "+userName);
	}

	/*
	 * Verify Email in the Comments are in proper format.
	 */
	@Test(dataProvider="emails",description="verify Email format in the Comments ",dependsOnMethods="verifyComments")
	public void verifyEmailFormat(String email){
		Assert.assertEquals(validateEmailFormat(email), true,"Email is not in proper Format "+email);
	}

	@DataProvider(name="emails")
	private Object[] mailIds(){
		return setOfComments.toArray();
	}


}
