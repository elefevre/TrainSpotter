


import static org.fest.assertions.Assertions.*;
import models.ResultsPageParser;
import org.fest.assertions.Assertions;
import org.junit.Test;
import play.test.UnitTest;

public class ResultsPageParserTest extends UnitTest {
	// <!-- saved from url=(0080)http://www.infolignes.com/recherche.php?date_num_train=2011|04|20&num_train=7015 -->
	private static final String sample_08_04_2011 = "\n"
			+ "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"> \n"
			+ //                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
			"<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"fr\"> \n"
			+ //
			"    <head> \n"
			+ //
			"        <title>Infolignes - Résultats de recherche</title> \n"
			+ //
			"        <meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\" /> \n"
			+ //
			"        <meta name=\"description\" content=\"\" /> \n"
			+ //
			"		<meta name=\"viewport\" content=\"width=device-width, minimum-scale=1.0, maximum-scale=1.0\" /> \n"
			+ //
			"		<link rel=\"shortcut icon\" type=\"image/ico\" href=\"favicon.ico\" /> \n"
			+ //
			"		<link rel=\"stylesheet\" type=\"text/css\" media=\"screen, projection\" href=\"css/screen.css\" /> \n"
			+ //
			"	    <!--[if IE 6]><link href=\"css/ie6.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" /><![endif]--> \n"
			+ //
			"		<!--[if IE 7]><link href=\"css/ie7.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" /><![endif]--> \n"
			+ //
			"		<!--[if IE 8]><link href=\"css/ie8.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" /><![endif]-->		\n"
			+ //
			"		<script type=\"text/javascript\">function addBookmark(){var browserName = navigator.appName;var browserVer = parseInt(navigator.appVersion,10);if(browserName == 'Microsoft Internet Explorer' && browserVer >= 4){window.external.AddFavorite(\"http://www.infolignes.com/\",\"InfoLignes\");} else{window.sidebar.addPanel(\"InfoLignes\",\"http://www.infolignes.com/\",\"\");}}\n"
			+ //
			"function toggle_visibility(id){var e = document.getElementById(id);if(e.style.display == 'block'){e.style.display = 'none';}else{e.style.display = 'block';}}</script> \n"
			+ //
			" \n"
			+ //
			"                    <script type=\"text/javascript\"> \n"
			+ //
			" \n"
			+ //
			"              var _gaq = _gaq || [];\n"
			+ //
			"              _gaq.push(['_setAccount', 'UA-22677850-1']);\n"
			+ //
			"              _gaq.push(['_trackPageview']);\n"
			+ //
			" \n"
			+ //
			"              (function() {\n"
			+ //
			"                var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;\n"
			+ //
			"                ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';\n"
			+ //
			"                var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);\n"
			+ //
			"              })();\n"
			+ //
			" \n"
			+ //
			"            </script> \n"
			+ //
			"        	</head> \n"
			+ //
			" \n"
			+ //
			"	<body> \n"
			+ //
			"		\n"
			+ //
			"        <script type=\"text/javascript\" src=\"/javascript/jquery-1.3.2.min.js\"></script><script type=\"text/javascript\" src=\"/javascript/jquery-json.min.js\"></script><script type=\"text/javascript\" src=\"/javascript/jquery.autocomplete.min.js\"></script><script type=\"text/javascript\" src=\"/javascript/jquery.ajaxQueue.min.js\"></script><script type=\"text/javascript\" src=\"/javascript/jquery.autocomplete.min.js\"></script><script type=\"text/javascript\" src=\"/javascript/jquery.bgiframe.min.js\"></script><script type=\"text/javascript\" src=\"/javascript/jquery.mb.mediaEmbedder.js\"></script><script type=\"text/javascript\" src=\"/javascript/jquery.media.js\"></script><script type=\"text/javascript\" src=\"/javascript/infolignes.js\"></script><script type=\"text/javascript\" src=\"/javascript/thickbox-compressed.js\"></script><script type=\"text/javascript\" src=\"/javascript/script.js\"></script><link rel=\"stylesheet\" type=\"text/css\" href=\"/css/jquery.autocomplete.min.css\" />		<div id=\"header\"> \n"
			+ //
			"		  <div class=\"gutter\"> \n" + //
			"		    <h1><a href=\"index.php\">SNCF Direct : L'info en temps réel</a></h1> \n" + //
			"		    <ul class=\"links\"> \n" + //
			"		      		      	<li id=\"radio\"><a href=\"#\" onclick=\"javascript:window.open('radio.html', 'SNCF_La_Radio', config='resizable=yes, location=no, width=320, height=220, menubar=no, status=no, scrollbars=no');\">la radio</a></li> \n" + //
			"		      	\n" + //
			"		      <li id=\"com\"><a href=\"http://www.sncf.com/\">sncf.com</a></li> \n" + //
			"		    </ul> \n" + //
			"		    <div id=\"nav\"><ul class=\"group n1\"><li id=\"accueil\"><a href=\"index.php\">Accueil</a></li><li id=\"previsions\"><a href=\"#\" onclick=\"return false;\">Les prévisions</a><ul class=\"n2\"><li><a href=\"/previsions.php?zoneId=1&zoneName=Nord\">Nord</a></li><li><a href=\"/previsions.php?zoneId=2&zoneName=Est\">Est</a></li><li><a href=\"/previsions.php?zoneId=3&zoneName=Sud-Est\">Sud-Est</a></li><li><a href=\"/previsions.php?zoneId=4&zoneName=Ouest-Sud-Ouest\">Ouest-Sud-Ouest</a></li></ul></li><li id=\"reportages\"><a href=\"reportages.php\">Reportages</a></li></ul></div>		  </div> \n" + //
			"		</div><div id=\"main\"> \n" + //
			"<span style=\"display:none;\" id=\"cache\"></span>                    	<div id=\"content\"> \n" + //
			"	      <div class=\"gutter\"> \n" + //
			"	      \n" + //
			"	        <div id=\"demande\"> \n" + //
			"  	        <h2><span>Train N°7015<img title=\"TGV\" alt=\"TGV\" src=\"/images/bandeau-train/logo-tgv.jpg\"/></span></h2> \n" + //
			"  	        <p class=\"help\"><a href=\"aide.php\">Aide</a></p> \n" + //
			"  	        <div class=\"block group\"> \n" + //
			"    	        <ul> \n" + //
			"    	          <li>Gare de départ : <strong>  Paris-Nord</strong></li> \n" + //
			"    	          <li>Gare d'arrivée : <strong>  Lille Flandres</strong></li> \n" + //
			"    	        </ul> \n" + //
			"    	        <ul> \n" + //
			"    	          <li>Heure de départ théorique : <strong>08h28</strong> &nbsp;<span style=\"font-size: 0.8em\" >(le 20/04/2011)</span></li> \n" + //
			"    	          <li>Heure d'arrivée théorique : <strong>09h30</strong></li> \n" + //
			"    	        </ul> \n" + //
			"    	      </div> \n" + //
			"  	      </div> \n" + //
			"  	         	      <div id=\"resultat\"> \n" + //
			"  	        <div class=\"block group\"> \n" + //
			"  	        	<div class=\"msg\"> \n" + //
			"	  	            	  	              	          		  	          			<p>Veuillez vous assurer de vos correspondances en cas de retard.</p> \n" + //
			"	  	            	<p>En cas de divergences avec les horaires inscrits sur votre billet, les horaires d'INFOLIGNES sont à prendre en compte.</p> \n" + //
			"  	          		  	          	</div> \n" + //
			"  	          <table class=\"resultat\"> \n" + //
			"  	            <caption>Résutlat de votre recherche</caption> \n" + //
			"  	            <thead> \n" + //
			"  	              <tr> \n" + //
			"  	                <th class=\"gares\"><span>Gares</span></th> \n" + //
			"  	                <th class=\"horaires\"><span>Horaires</span></th> \n" + //
			"  	                <th class=\"info-train\"><span>Informations</span></th> \n" + //
			"  	              </tr> \n" + //
			"  	            </thead> \n" + //
			"  	            <tbody> \n" + //
			"                                                                    <tr > \n" + //
			"                            <td>  Paris-Nord</td> \n" + //
			"                            <td class=\"center\"> \n" + //
			"                                	                                	                                    dep.&nbsp;08h28\n" + //
			"	                                                               	                            </td> \n" + //
			"                            <td class=\"center\"> \n" + //
			"                            	                            	                                A l'heure\n" + //
			"	                            	                                                                                                                                            </td> \n" + //
			"                        </tr> \n" + //
			"                                                                    <tr > \n" + //
			"                            <td>  Lille Flandres</td> \n" + //
			"                            <td class=\"center\"> \n" + //
			"                                	                                	                                    arr.&nbsp;09h30\n" + //
			"	                                                               	                            </td> \n" + //
			"                            <td class=\"center\"> \n" + //
			"                            	                            	                                A l'heure\n" + //
			"	                            	                                                                                                                                            </td> \n" + //
			"                        </tr> \n" + //
			"                      	            </tbody> \n" + //
			"  	          </table> \n" + //
			"  	        </div> \n" + //
			"  	      </div> \n" + //
			"  	      \n" + //
			"            \n" + //
			"      	      		      </div> \n" + //
			"	    </div> \n" + //
			"         \n" + //
			"</div> \n" + //
			"<div id=\"partenaires\"> \n" + //
			"	<div class=\"gutter\">  \n" + //
			"    	<p>       \n" + //
			"       		<a href=\"http://www.idtgv.com/\"><img src=\"images/logo-idtgv.jpg\" alt=\"IDTGV\" /></a> \n" + //
			"		    <span class=\"cacher\"> | </span> \n" + //
			"		    <a href=\"http://www.intercites.com/\"><img src=\"images/logo-intercites.jpg\" alt=\"InTercités\" /></a> \n" + //
			"		    <span class=\"cacher\"> | </span> \n" + //
			"		    <a href=\"http://coraillunea.fr/destinations/index.html\"><img src=\"images/logo-lunea.jpg\" alt=\"Lunéa\" /></a> \n" + //
			"		    <span class=\"cacher\"> | </span> \n" + //
			"		    <a href=\"http://teoz.voyages-sncf.com/\"><img src=\"images/logo-teoz.jpg\" alt=\"Téoz\" /></a> \n" + //
			"		    <span class=\"cacher\"> | </span> \n" + //
			"		    <a href=\"http://www.ter-sncf.com/\"><img src=\"images/logo-ter.jpg\" alt=\"ter\" /></a> \n" + //
			"		    <span class=\"cacher\"> | </span> \n" + //
			"		    <a href=\"http://www.tgv.com/\"><img src=\"images/logo-tgv.jpg\" alt=\"TGV\" /></a> \n" + //
			"            <span class=\"cacher\"> | </span> \n" + //
			"            <a href=\"http://www.transilien.com/\"><img src=\"images/logo-transilien.jpg\" alt=\"transilien\" /></a> \n" + //
			"            <span class=\"cacher\"> | </span> \n" + //
			"		</p> \n" + //
			"  </div> \n" + //
			"</div> \n" + //
			"<div id=\"footer\"> \n" + //
			"  <div class=\"gutter\">  \n" + //
			"    <p> \n" + //
			"      <a href=\"plan.php\">Plan du site</a> | <a href=\"mentions_legales.php\">Mentions légales</a> | <a href=\"credit.php\">Crédits</a> \n" + //
			"    </p> \n" + //
			"  </div> \n" + //
			"</div>    \n" + //
			"</body> \n" + //
			"</html><script type=\"text/javascript\"> \n" + //
			"$(function() {\n" + //
			"	$('#form_search').submit(function() {\n" + //
			"		$('#divErrorSearch').hide();\n" + //
			"        var valid = true;\n" + //
			"		var msg = '';\n" + //
			" \n" + //
			"	if($('#num_train').val() == '') {\n" + //
			"		\n" + //
			"		if($('#formType').val() == 'nd')\n" + //
			"		{\n" + //
			"	        if ($('#station').val() == '' && ($('#num_train').val() == '-' || $('#num_train').val() == '')) {\n" + //
			"	        	msg += \"Veuillez saisir un n° de train ou une gare<br>\";\n" + //
			"	            valid = false;\n" + //
			"	        }\n" + //
			"	        if (($('#num_train').val() == '-' || $('#num_train').val() == '') && $('#next_horaire').val() == '-1') {\n" + //
			"	        	msg += 'Veuillez renseigner l\\'heure des prochains départs ou arrivées<br>';\n" + //
			"	            valid = false;\n" + //
			"	        }\n" + //
			"	\n" + //
			"	        if (($('#num_train').val() == '-' || $('#num_train').val() == '') && !$('#sens_depart').is(':checked') && !$('#sens_arrivee').is(':checked')) {\n" + //
			"	        	msg += 'Veuillez indiquer si vous voulez les départs ou les arrivées<br>';\n" + //
			"	            valid = false;\n" + //
			"	        }\n" + //
			"		}\n" + //
			"	    else if($('#formType').val() == 'od')\n" + //
			"	    {\n" + //
			"	        if (($('#cityDepart').val() == '' || $('#cityDestination').val() == '') && ($('#num_train').val() == '-' || $('#num_train').val() == '')) {\n" + //
			"	        	msg += \"Veuillez saisir un n°de train ou les villes de départ et d'arrivée<br>\";\n" + //
			"	            valid = false;\n" + //
			"	        }\n" + //
			"	        if (($('#num_train').val() == '-' || $('#num_train').val() == '') && $('#train_horaire').val() == '-1') {\n" + //
			"	        	msg += 'Veuillez renseigner l\\'heure de départ ou d\\'arrivée<br>';\n" + //
			"	            valid = false;\n" + //
			"	        }\n" + //
			"	        if (!$('#type_heure_depart').is(':checked') && !$('#type_heure_arrivee').is(':checked')) {\n" + //
			"	        	msg += 'Veuillez indiquer si vous partez ou arrivez à cette heure<br>';\n" + //
			"	            valid = false;\n" + //
			"	        }\n" + //
			"		}\n" + //
			"	}\n" + //
			"        if(!valid)\n" + //
			"        {\n" + //
			"        	msg = '<label><font color=\"white\" size=\"2\">' + msg + '</font></label>';\n" + //
			"        	$('#divErrorSearch').html(msg);\n" + //
			"        	$('#divErrorSearch').show();\n" + //
			"        }\n" + //
			" \n" + //
			"        return valid;\n" + //
			"        \n" + //
			"    });\n" + //
			"    buildAutocomplete('depart', '/class/Ajax.php?method=firstLetter', 'cityDepart');\n" + //
			"    buildAutocomplete('destination', '/class/Ajax.php?method=firstLetter', 'cityDestination');\n" + //
			"    buildAutocomplete('via', '/class/Ajax.php?method=firstLetter', 'cityVia');\n" + //
			"    buildAutocomplete('station', '/class/Ajax.php?method=firstLetterStation', 'stationCode');\n" + //
			"    \n" + //
			"    });\n" + //
			"</script> ";

	@Test
	public void shouldReadTheTrainNumber() {
		Assertions.assertThat(new ResultsPageParser(sample_08_04_2011).getTrainNumber()).isEqualTo("7015");
	}

	@Test
	public void shouldReadTheDepartureDate() {
		Assertions.assertThat(new ResultsPageParser(sample_08_04_2011).getDepartureDate()).isEqualTo("20/04/2011");
	}

}
