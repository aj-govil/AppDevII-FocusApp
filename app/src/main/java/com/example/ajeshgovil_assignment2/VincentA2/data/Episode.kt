package com.example.woof.data

import androidx.annotation.DrawableRes
import com.example.ajeshgovil_assignment2.R
import java.util.Date

/**
 * A data class to represent an episode presented in the episode card
 */

data class Episode (
    @DrawableRes val imageIcon : Int,
    val position : String,
    val title : String,
    val description: String,
    val releaseDate: String,
    val rank : Float

)

val episodes = listOf(
    Episode(
        R.drawable.ozymmandias,"S5 E14","Ozymandias",
        "Walt goes on the run. Jesse is taken hostage. Marie forces Skyler to tell Walter, Jr. the truth.", "Sun, Sep 15, 2013" ,10f),
    Episode(
        R.drawable.felina,"S5 E16","Felina",
        "Walter White returns to Albuquerque one last time to secure his family's future and settle old scores.", "Mon, Sep 30, 2013" ,9.9f),
    Episode(
        R.drawable.faceoff,"S4 E13","Face Off",
        "Jesse is brought to the FBI for questioning on his knowledge of ricin. In a last effort to kill Gus, Walt must ask for help from an old enemy.", "Sun, Oct 9, 2011" ,9.9f),
    Episode(
        R.drawable.tohajiilee,"S5 E13","To'hajiilee",
        "Jesse and Hank come up with an idea to take Walt down. Walt hires Todd's uncle to kill Jesse.", "Sun, Sep 8, 2013" ,9.8f),
    Episode(
        R.drawable.granitestate,"S5 E15","Granite State",
        "Walt struggles as he adapts to aspects of his new identity. Jesse plans an escape against Jack and his crew.", "Sun, Sep 22, 2013" ,9.7f),
    Episode(
        R.drawable.deadfright,"S5 E5","Dead Fright",
        "Walter White's domestic life continues to deteriorate; at the same time, his growing business venture aims to steal a train car chock full of methylamine.", "Sun, Aug 12, 2012" ,9.7f),
    Episode(
        R.drawable.crawlspace,"S4 E11","Crawl Space",
        "Hank asks Walt to drive him to the laundry where the meth lab is hidden. Ted still won't pay the IRS so Skyler asks Saul for help, and Saul sends in his A-Team. Gus and Jesse return from Mexico, and Walt fears he is in trouble with Gus.", "Sun, Sep 15, 2011" ,9.7f),
    Episode(
        R.drawable.fullmeasure,"S3 E13","Full Measure",
        "Jesse has disappeared and Walt is in big trouble with Gus. So Gus rehires Gale to learn from Walt's cooking so that they can dispose of Walt once and for all.", "Sun, Jun 13, 2010" ,9.7f),
    Episode(
        R.drawable.confessions,"S5 E11","Confessions",
        "Walt makes his next move against Hank, putting Hank in a tough position. Hank confronts Jesse and attempts to turn him against Walt, and Jesse decides it's time to move on. Meanwhile, Todd has taken over the business.", "Sun, Aug 25, 2013" ,9.6f),
    Episode(
        R.drawable.saymyname,"S5 E7","Say My Name",
        "Mike and Jesse are out. Now Walt has to handle things on his own. Hank finally finds a rat in Mike's gang.", "Sun, Aug 26, 2012" ,9.6f)

)



