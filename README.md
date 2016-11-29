# HawkVision
Team 2554 Vision Processing
Current Capabilities:

-Pull camera data into a Mat element

-Convert rgba colorspace to hsv colorspace

-Filter image for certain colors within a range

-Find contours of colors

Needs to be Done:
-Identify target based on certain characteristics

    -Area larger than a threshhold to filter out noise.
    
    Then perform following tests and assign a total score for each contour based on how well of a fit it is:
    
       -Straightness of edges
       
       -Area of Convex Hull contour V.S. Area of polygon contour (should be around 30ish %)
       
       -Aspect Ratio?
       
-Return coordinates of polygon and display vertical and horizontal line going to the center on screen

-Send coordinates over ethernet wire attached to phone
