<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Tabel</title>
<style>
* {font-family:Verdana; font-size:12px;}
table {background:#707070;border-collapse: separate;border-spacing: 1px;}
td {background:#ffffff;padding:2px;}
th {background:#f0f0f0;padding:2px;}
</style>
</head>
<body>
<?php

require_once('andmed.php');

// some html table functions here
function td($string)	{echo "\t\t<td>$string</td>\n";}
function th($string)	{echo "\t\t<th>$string</th>\n";}
function tr_begin()	    {echo "\t<tr>\n";}
function tr_end()	    {echo "\t</tr>\n";}
function table_begin()	{echo "<table>\n";};
function table_end()	{echo "</table>\n";};


function to_hours($string) {
	$split = explode(":",$string);
	$hours = $split[0];
	$hours += $split[1]/60;
	return $hours;
}

// table headers
$table_cols = array(
	'Name',
	'Begin',
	'End',
	'Duration',
	'Day Hours',
	'Night Hours');

// night config
$night_start = to_hours($SETTINGS_nighttime_start);
$night_end = to_hours($SETTINGS_nighttime_end);

// show info about night time 
echo "Night Start: ".$SETTINGS_nighttime_start."<br>";
echo "Night End: ".$SETTINGS_nighttime_end."<br>";

// draw table beginningand header
table_begin();
tr_begin();
foreach ($table_cols as $col) th($col);
tr_end();

// iterate trough employees
foreach($EMPLOYEES as $id => $employee_arr) {
	tr_begin();
	td($employee_arr['name']);
	td($employee_arr['shift_start']);
	td($employee_arr['shift_end']);

	// init work hour counters
	$work_at_day = 0;
	$work_at_night = 0;
	$work_duration = 0;

	// lets get the float hours
	$work_start = to_hours($employee_arr['shift_start']);
	$work_end = to_hours($employee_arr['shift_end']);
	
	// to ease up things we will look at this at 0 to 48 hour scale, and create assumption that
	// nobody works longer than 48 hours as input data does not provide this anyway.
	
	// we have 'two' night time endings, on day #0 and day #1
	$night_end_2nd_day = $night_end + 24;
	
	// if work is passing on to second day add 24 hours to $work_end time
	if($work_end < $work_start) $work_end += 24;
	
	// now we can easily calculate overall work duration
	$work_duration = $work_end - $work_start;
	
	// lets calculate night hours for first night part (not in sample data)
	// but in real life very possible
	if($work_start < $night_end && $work_end > $night_end) {
		$work_at_night += $night_end - $work_start;
	}
	else if($work_start < $night_end && $work_end <= $night_end){
		$work_at_night += $work_end - $work_start;
	}
	
	// lets calcualte night hours for second night time
	if($work_start < $night_start && $work_end > $night_start) {
		if($work_end < $night_end_2nd_day) {
			$work_at_night += $work_end - $night_start;
		}
		else if($work_end > $night_end_2nd_day) {
			$work_at_night += $night_end_2nd_day - $night_start;
		}
	}
	else if($work_start > $night_start) {
		if($work_end < $night_end_2nd_day) {
			$work_at_night += $work_end - $work_start;
		}
		else if ($work_end > $night_end_2nd_day) {
			$work_at_night += $night_end_2nd_day - $work_start;
		}
	}
	
	// easy to get day working time
	$work_at_day = $work_duration - $work_at_night;
	
	td($work_duration);
	td($work_at_day);
	td($work_at_night);
	tr_end();
}

table_end();
?>
</body>
</html>