# PhpStorm IDE Plugin:<br>PHP Advanced AutoComplete#

Plugin URL: http://plugins.jetbrains.com/plugin/7276?pr=phpStorm

Adds auto-completion support for various built-in PHP functions and methods, where parameter is a string literal.


The following functions are currently supported:

* <b>header / header_remove</b><br>
    HTTP response headers, status codes, charsets, mime-types, locations, and much more

* <b>File and folder related functions and methods (fopen, file_get_contents, dir...)</b><br>
    Files and/or folders paths relative to the current file (completion and reference)

* <b>date</b><br>
    Format characters and common format strings

* <b>htmlentities / htmlspecialchars</b><br>
    Supported charsets

* <b>mb_string functions</b><br>
    Charset, where required; types for mb_get_info and supported languages for mb_language

* <b>ini_get / ini_set / ini_restore / get_cfg_var</b><br>
    Known INI variable names

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

<b>Important:</b><br>
You have to start a string literal and press <i>Ctrl</i> + <i>Space</i> to activate the completion popup

If you have further suggestions/ideas, just send me an e-mail to <i>phpstorm at king2500.net</i>.
