# AutoResortLock
Automated program to generate a lock code through resortlock.com

## Overview
This program was created to speed up the time it takes to generate a random lock code for a Maui condo for renters. This program takes the login information of the owner and the checkin and checkout dates of the renters. When run, the program goes to the Resort Lock webpage, logs in, and enters the necessary information to generate the lock code. From here, the owner can send this code to the renters so that they will have access to the condo for the set period of time.

## AutoResortLock.java
This is the main program to generate a lock code. It uses the Firefox webdriver in the `WebDrivers` folder. It also uses Selenium which is a testing framework that utilizes WebDrivers to autimate webpage tests.

#### Usage
`java -jar AutoResortLock username password startDate endDate`

## AutoResortLockCalendar.java
This program doesn't generate a code but adds the stay within the website's calendar. This program also uses the Firefox webdriver and utilizes other parts of the Selenium libraries to traverse the webpages.
