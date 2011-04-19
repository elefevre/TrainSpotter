package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class TrainSpotter extends Controller {

	public static void displayTrainDetails(String trainNumber) {
		render(trainNumber);
	}
}