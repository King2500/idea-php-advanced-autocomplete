# PhpStorm IDE Plugin:<br>PHP Advanced AutoComplete
[![Version](http://phpstorm.espend.de/badge/7276/version)](https://plugins.jetbrains.com/plugin/7276)
[![Downloads](http://phpstorm.espend.de/badge/7276/downloads)](https://plugins.jetbrains.com/plugin/7276)
[![Downloads last month](http://phpstorm.espend.de/badge/7276/last-month)](https://plugins.jetbrains.com/plugin/7276)

Plugin URL: https://plugins.jetbrains.com/plugin/7276

Adds auto-completion support for various built-in PHP functions and methods, where parameter is a string literal.


The following functions are currently supported:

* <b>header / header_remove</b><br>
    HTTP response headers, status codes, charsets, mime-types, locations, and much more

* <b>File and folder related functions and methods (fopen, file_get_contents, dir...)</b><br>
    Files and/or folders paths relative to the current file (completion and reference)

* <b>date / strftime / DateTime::format etc</b><br>
    Format characters and common format strings

* <b>strtotime / DateTime constructor / DateTime::modify</b><br>
    DateTime notations

* <b>htmlentities / htmlspecialchars</b><br>
    Supported charsets

* <b>mb_string functions</b><br>
    Charset, where required; types for mb_get_info and supported languages for mb_language

* <b>extension_loaded</b><br>
    Known PHP extensions

* <b>fopen / popen / SplFileInfo::openFile</b><br>
    File modes

* <b>mysql_connect/mysqli_connect/mysqli/PDO</b><br>
    Hostnames, database names and usernames from data sources defined in project

* <b>mysql_select_db/mysqli_select_db/mysqli::select_db</b><br>
    Database names from data sources defined in project

* <b>mysqli_change_user/mysqli::change_user</b><br>
    Usernames and database names from data sources defined in project

* <b>mysql_set_charset/mysqli_set_charset/mysqli::set_charset</b><br>
    Supported charsets for MySQL

* <b>getenv</b><br>
    Common environment variables (like in $_SERVER array keys)   

If you have further suggestions/ideas, just send me an e-mail to <i>phpstorm at king2500.net</i>.
