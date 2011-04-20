package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;

import java.util.*;

import models.*;

public class TrainSpotter extends Controller {

	public static void displayTrainDetails(@Required String trainNumber) {
		if (validation.hasErrors()) {
			flash.error("Please enter the train number");
			Application.index();
		}

		render(trainNumber);
	}
} 