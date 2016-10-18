package edu.csupomona.cs480.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.csupomona.cs480.App;
import edu.csupomona.cs480.data.User;
import edu.csupomona.cs480.data.provider.UserManager;

import junit.framework.*;


/**
 * This is the controller used by Spring framework.
 * <p>
 * The basic function of this controller is to map
 * each HTTP API Path to the correspondent method.
 *
 */

@RestController
public class WebController {

	/**
	 * When the class instance is annotated with
	 * {@link Autowired}, it will be looking for the actual
	 * instance from the defined beans.
	 * <p>
	 * In our project, all the beans are defined in
	 * the {@link App} class.
	 */
	@Autowired
	private UserManager userManager;
	/**
	 * This is Elinor Huntington'sgi Assignment 3 request mapping api
	 * It returns a string for now, and hopefully something
	 * more exciting for assignment 4
	 */
	@RequestMapping(value = "/cs480/equipo", method = RequestMethod.GET)
	String equipo() {
		return "Hello from equipo!";
	}
	/**
	 * This is Bhavana
	 * Asssignment 3 part 3
	 */
	@RequestMapping(value = "/cs480/A3", method = RequestMethod.GET)
	String A3() {
		return "Hey Bhavana!";
	}
	/**
	 * Assignment 3 part 3
	 * Method by Fanny Avila
	 * returns a string to be displayed
	 * string changes every couple of hours depending on what the time is. 
	 * Method determines current coffee level depending on what hour of the day it is 
	 * These numbers are taken from personal experience.
	 * */
	@RequestMapping(value = "/cs480/coffee_status", method = RequestMethod.GET)
	String CoffeeStatus() {
		float percentCoffee=0;
		Date date = new Date();   // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date);   // assigns calendar to given date 
		float hour = calendar.get(Calendar.HOUR_OF_DAY);
		String message="";
		percentCoffee= (100-((hour/24)*100));
		if(percentCoffee >= 90){
			message=message+"no action required...The jitters should start soon.";
		}
		else if(percentCoffee <90 && percentCoffee >=70){
			message=message+"Everything is right with the world.";
		}
		else if(percentCoffee <70 && percentCoffee>=50)
		{
			message=message+"should probably get started on that project...";
		}
		else if(percentCoffee<50 && percentCoffee>=30){
			message = message+"...I should get to work on that project....I'll just lay down for a bit";
		}
		else if(percentCoffee < 30 && percentCoffee >= 12)
		{
			message = message+"...I need a nap...";
		}
		else if(percentCoffee<12 && percentCoffee>=5){
			message = message+"WARNING!: BATTERY DANGEROUSLY LOW! please grab another cup.";
		}
		else{
			message = message+"SHUTING DOWN...Zzzzzzzzzzzzzzz...";
		}
			
		return "Current coffee levels: "+ String.format(java.util.Locale.US,"%.2f", percentCoffee)+"%" +" "+message;
	}
	
	/**
	 * Cristian Garcia
	 * Assignment 4 Pt. 2
	 * Implementing junit into project
	 */
	@RequestMapping(value = "/cs480/junit-demo", method = RequestMethod.GET)
	String junitDemo() {
		
		int value1 = 2;
		int value2 = 3;
		
		double result = value1 + value2;
		assertTrue(result == 6);
		
	}
	
	/**
	 * This is a simple example of how the HTTP API works.
	 * It returns a String "OK" in the HTTP response.
	 * To try it, run the web application locally,
	 * in your web browser, type the link:
	 * 	http://localhost:8080/cs480/ping
	 */
	@RequestMapping(value = "/cs480/ping", method = RequestMethod.GET)
	String healthCheck() {
		// You can replace this with other string,
		// and run the application locally to check your changes
		// with the URL: http://localhost:8080/
		return "OK";
	}

	/**
	 * This is a simple example of how to use a data manager
	 * to retrieve the data and return it as an HTTP response.
	 * <p>
	 * Note, when it returns from the Spring, it will be
	 * automatically converted to JSON format.
	 * <p>
	 * Try it in your web browser:
	 * 	http://localhost:8080/cs480/user/user101
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.GET)
	User getUser(@PathVariable("userId") String userId) {
		User user = userManager.getUser(userId);
		return user;
	}

	/**
	 * This is an example of sending an HTTP POST request to
	 * update a user's information (or create the user if not
	 * exists before).
	 *
	 * You can test this with a HTTP client by sending
	 *  http://localhost:8080/cs480/user/user101
	 *  	name=John major=CS
	 *
	 * Note, the URL will not work directly in browser, because
	 * it is not a GET request. You need to use a tool such as
	 * curl.
	 *
	 * @param id
	 * @param name
	 * @param major
	 * @return
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.POST)
	User updateUser(
			@PathVariable("userId") String id,
			@RequestParam("name") String name,
			@RequestParam(value = "major", required = false) String major) {
		User user = new User();
		user.setId(id);
		user.setMajor(major);
		user.setName(name);
		userManager.updateUser(user);
		return user;
	}

	/**
	 * This API deletes the user. It uses HTTP DELETE method.
	 *
	 * @param userId
	 */
	@RequestMapping(value = "/cs480/user/{userId}", method = RequestMethod.DELETE)
	void deleteUser(
			@PathVariable("userId") String userId) {
		userManager.deleteUser(userId);
	}

	/**
	 * This API lists all the users in the current database.
	 *
	 * @return
	 */
	@RequestMapping(value = "/cs480/users/list", method = RequestMethod.GET)
	List<User> listAllUsers() {
		return userManager.listAllUsers();
	}

	/*********** Web UI Test Utility **********/
	/**
	 * This method provide a simple web UI for you to test the different
	 * functionalities used in this web service.
	 */
	@RequestMapping(value = "/cs480/home", method = RequestMethod.GET)
	ModelAndView getUserHomepage() {
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("users", listAllUsers());
		return modelAndView;
	}

}