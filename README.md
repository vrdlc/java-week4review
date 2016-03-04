# java-week4review

# Bands and the Venues They Play
#Java Week 4 Review
#####for Epicodus
####V. Ramon de la Cruz
####vrdlc.github.io

##Description

This program will allow users to add bands, with genre, and venues, with venue style. The bands can then be assigned venues to play. Bands can be deleted individually, and when they are, that removes them from every venue they were assigned to. 


##Setup
###### 1. Clone the directory to your desktop.  
2. Start postgres in a terminal window, then minimize it. Open a new terminal window and type "psql". Then create a database called "bands_venues". Type the following command: "pg_dump bands_venues > bands_venues.sql". This will automatically create the tables you will need to use this program. 

3. In your terminal, open a new tab. Go into the cloned folder on your desktop (java-week4review) and run Gradle, then go to http://localhost:4567 in your browser. 

4. Enter a band name and genre of music into the fields. When you click the Submit button, that band name will be appended to the left column below the forms. You can do the same for venues using the bottom two forms. Venues will then be appended into the column on the right. Inside the listing for an individual band, you can assign a venue for them to play in and vice versa. Any of this information can be updated later if you so choose. 

5. In each individual band and venue page, there is a delete button. Click on this button to delete the individual listing. This will remove the listing from its corresponding partners as well. On the index page, there are large, red Delete All buttons and they are no joke. If you click these buttons, it will not only delete the entries from the pages, it will also clear their respective database tables, so use only in an emergency and at your own risk. 

6. I tried to implement a way to add show dates for venues and bands in the join table, but I think it's beyond my ken. If you have any ideas, feel free to fork this project and submit a pull request. I'd love to figure out how to do that.  


#Technologies Used
* HTML
* CSS
* Bootstrap
* Java
* Git
* Spark
* Gradle
* Velocity Template Engine
* psql
And, of course,
* a text editor and
* a terminal

Copyright (c) 2016 V. Ramon de la Cruz

This software is licensed under the MIT license.
