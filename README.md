# PhpStorm IDE Plugin:<br>PHP Advanced AutoComplete#

Plugin URL: http://plugins.jetbrains.com/plugin/7276?pr=phpStorm

Adds auto-completion support for various built-in PHP functions, where parameter is a string literal.


The following functions are currently supported:

* header/header_remove<br>
    HTTP response headers, status codes, charsets, mime-types, and much more

* date<br>
    Format characters and common format strings

* htmlentities/htmlspecialchars<br>
    Supported charsets

* mb_string functions<br>
    Charset, where required; types for mb_get_info and supported languages for mb_language

* ini_get/ini_set/ini_restore/get_cfg_var<br>
    Known INI variable names

* extension_loaded<br>
    Known PHP extensions

* fopen/popen<br>
    File modes

* mysql_connect/mysqli_connect<br>
    Currently only &quot;localhost&quot; for host parameter<br><br></li>

Important:<br>
You have to start a string literal and press <i>Ctrl</i> + <i>Space</i> to activate the completion popup

If you have further suggestions/ideas, just send me an e-mail to <i>phpstorm at king2500.net</i>.