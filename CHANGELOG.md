Changelog
=========

### 1.1.0
* Added completion support for DateTime constructor / DateTime::modify / strtotime DateTime notations
* Added completion for environment variables in getenv() ([#11](https://github.com/King2500/idea-php-advanced-autocomplete/issues/11))
* Added support for DateTimeImmutable (DateTime formats and notations) ([#12](https://github.com/King2500/idea-php-advanced-autocomplete/issues/12))
* AutoPopup completion implemented
* Improved file reference contributor
* Updated HTTP header completion tokens
* Changed HTTP header field completion to insert space char
* Fixed HTTP header field completions after colon ([#5](https://github.com/King2500/idea-php-advanced-autocomplete/issues/5))
* Fixed full header completion in header_remove() function
* Fixed prefixed completion for DateTime notations (also [#5](https://github.com/King2500/idea-php-advanced-autocomplete/issues/5))
* Fixed completion auto-popup for '-' char in date formats
* Fixed date format infos for ISO-8601 weeks
* Fixed duplicate completion entries when PHP Toolbox plugin is installed ([#15](https://github.com/King2500/idea-php-advanced-autocomplete/issues/15))
* Changes to compile against JDK 1.8 / PhpStorm 2019.1
* Minimum IDEA version changed to 2018.2+

### 1.0.4
* Changed date format info for 'W' ([#1](https://github.com/King2500/idea-php-advanced-autocomplete/pull/1))
* Fixed StringIndexOutOfBoundsException: String index out of range ([#2](https://github.com/King2500/idea-php-advanced-autocomplete/issues/2))

### 1.0.3
* Added support for date and time format characters for date_format, DateTime::format, DateTime::createFromFormat,
  date_create_from_format, strftime, gmstrftime and strptime

### 1.0.2
* Added support for completion and file reference (Ctrl+Click, Rename..) for various file and folder related functions and methods
* Added file mode completion support for SplFileInfo::openFile
* Added basic file url completion support for header('Location: ... and header('Content-Location: ...

### 1.0.1
* Added infos for date format characters
* Added infos for fopen/popen file modes
* Added completion support for mysql/mysqli/PDO connect functions, listing hostnames, database names and usernames from data sources defined in project
* Added completion support for MySQL charsets in mysql_set_charset, mysqli_set_charset, mysqli::set_charset
* Disabled case sensitivity for charsets/encodings (HTML, mbstring, header)

### 1.0.0
* Initial release
