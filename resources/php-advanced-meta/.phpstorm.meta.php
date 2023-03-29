<?php
namespace PHPSTORM_META {

    /**
     * Adds injection of project file reference in string literals for specified method or function.
     *
     * @param mixed  $functionReference Method or function reference, like: \FQN\Foo::bar()
     * @param int    $argumentIndex     Zero-based index of argument
     * @param string $relativeMode      Decides if root for completion is project top-level or directory of current file
     * @return string
     */
    function xAdvancedInjectFileReference($functionReference, $argumentIndex, $relativeMode = RELATIVE_CURRENT_FILE) {
        return "xAdvancedInjectFileReference " . $functionReference . " at " . $argumentIndex . ": " . $relativeMode;
    }

    /**
     * Adds injection of project folder reference in string literals for specified method or function.
     *
     * @param mixed  $functionReference Method or function reference, like: \FQN\Foo::bar()
     * @param int    $argumentIndex     Zero-based index of argument
     * @param string $relativeMode      Decides if root for completion is project top-level or directory of current file
     * @return string
     */
    function xAdvancedInjectDirectoryReference($functionReference, $argumentIndex, $relativeMode = RELATIVE_CURRENT_FILE) {
        return "xAdvancedInjectDirectoryReference " . $functionReference . " at " . $argumentIndex . ": " . $relativeMode;
    }

    function xAdvancedCompletion($functionReference, $argumentIndex, $completionList) {
        return "xAdvancedCompletion " . $functionReference . " at " . $argumentIndex . ": " . $completionList;
    }

    //const RELATIVE_AUTO = '';

    /** Completion relative from project root */
    const RELATIVE_TOP_LEVEL = '/';

    /** Completion relative from directory of current file */
    const RELATIVE_CURRENT_FILE = '.';

    registerArgumentsSet('x_advanced_fileref_relative_modes', \PHPSTORM_META\RELATIVE_TOP_LEVEL, \PHPSTORM_META\RELATIVE_CURRENT_FILE);
    expectedArguments(\PHPSTORM_META\xAdvancedInjectFileReference(), 2, argumentsSet('x_advanced_fileref_relative_modes'));
    expectedArguments(\PHPSTORM_META\xAdvancedInjectDirectoryReference(), 2, argumentsSet('x_advanced_fileref_relative_modes'));

    registerArgumentsSet('x_advanced_completion_lists',
        'http_response_header_name',
        'http_response_header_string',
        'http_request_header_name',
        'http_method',
        'http_cachecontrol_directive',
        'http_encoding_token',
        'http_charset',
        'http_statuscode_text',
        'iso_language_code',
        'mime_type',
        'file_mode',
        'pack_format_code',
        'unpack_format_code',
        'format_spec',
        'scan_format_spec',
        'php_extension',
        'html_charset',
        'mb_encoding',
        'mb_info_type',
        'mb_language',
        'ob_handler',
        'socket_transport',
        'date_format',
        'strftime_format',
        'datetime_token',
        'env_var'
    );
    expectedArguments(\PHPSTORM_META\xAdvancedCompletion(), 2, argumentsSet('x_advanced_completion_lists'));

    xAdvancedInjectFileReference(\basename(), 0);
    xAdvancedInjectFileReference(\chgrp(), 0);
    xAdvancedInjectFileReference(\chmod(), 0);
    xAdvancedInjectFileReference(\chown(), 0);
    xAdvancedInjectFileReference(\clearstatcache(), 1);
    xAdvancedInjectFileReference(\copy(), 0);
    xAdvancedInjectFileReference(\copy(), 1);
    xAdvancedInjectFileReference(\dirname(), 0);
    xAdvancedInjectFileReference(\file_exists(), 0);
    xAdvancedInjectFileReference(\file_get_contents(), 0);
    xAdvancedInjectFileReference(\file_put_contents(), 0);
    xAdvancedInjectFileReference(\file(), 0);
    xAdvancedInjectFileReference(\fileatime(), 0);
    xAdvancedInjectFileReference(\filectime(), 0);
    xAdvancedInjectFileReference(\filegroup(), 0);
    xAdvancedInjectFileReference(\fileinode(), 0);
    xAdvancedInjectFileReference(\filemtime(), 0);
    xAdvancedInjectFileReference(\fileowner(), 0);
    xAdvancedInjectFileReference(\fileperms(), 0);
    xAdvancedInjectFileReference(\filesize(), 0);
    xAdvancedInjectFileReference(\filetype(), 0);
    xAdvancedInjectFileReference(\fopen(), 0);
    xAdvancedInjectFileReference(\is_executable(), 0);
    xAdvancedInjectFileReference(\is_file(), 0);
    xAdvancedInjectFileReference(\is_link(), 0);
    xAdvancedInjectFileReference(\is_readable(), 0);
    xAdvancedInjectFileReference(\is_writable(), 0);
    xAdvancedInjectFileReference(\is_writeable(), 0);
    xAdvancedInjectFileReference(\lchgrp(), 0);
    xAdvancedInjectFileReference(\lchown(), 0);
    xAdvancedInjectFileReference(\link(), 0);
    xAdvancedInjectFileReference(\link(), 1);
    xAdvancedInjectFileReference(\linkinfo(), 0);
    xAdvancedInjectFileReference(\lstat(), 0);
    xAdvancedInjectFileReference(\move_uploaded_file(), 0);
    xAdvancedInjectFileReference(\move_uploaded_file(), 1);
    xAdvancedInjectFileReference(\parse_ini_file(), 0);
    xAdvancedInjectFileReference(\pathinfo(), 0);
    xAdvancedInjectFileReference(\readfile(), 0);
    xAdvancedInjectFileReference(\readlink(), 0);
    xAdvancedInjectFileReference(\realpath(), 0);
    xAdvancedInjectFileReference(\rename(), 0);
    xAdvancedInjectFileReference(\rename(), 1);
    xAdvancedInjectFileReference(\stat(), 0);
    xAdvancedInjectFileReference(\symlink(), 0);
    xAdvancedInjectFileReference(\symlink(), 1);
    xAdvancedInjectFileReference(\touch(), 0);
    xAdvancedInjectFileReference(\unlink(), 0);
    xAdvancedInjectFileReference(\SplFileInfo::__construct(), 0);
    xAdvancedInjectFileReference(\SplFileObject::__construct(), 0);
    xAdvancedInjectFileReference(\ZipArchive::open(), 0);
    xAdvancedInjectFileReference(\ZipArchive::addFile(), 0);
    xAdvancedInjectFileReference(\DOMDocument::load(), 0);
    xAdvancedInjectFileReference(\simplexml_load_file(), 0);
    xAdvancedInjectFileReference(\SimpleXMLElement::asXML(), 0);
    xAdvancedInjectFileReference(\SimpleXMLElement::saveXML(), 0);
    xAdvancedInjectFileReference(\XMLWriter::openUri(), 0);
    xAdvancedInjectFileReference(\xmlwriter_open_uri(), 0);
    xAdvancedInjectFileReference(\XMLReader::open(), 0);
    xAdvancedInjectFileReference(\imagecreatefrombmp(), 0);
    xAdvancedInjectFileReference(\imagecreatefromgd(), 0);
    xAdvancedInjectFileReference(\imagecreatefromgd2(), 0);
    xAdvancedInjectFileReference(\imagecreatefromgd2part(), 0);
    xAdvancedInjectFileReference(\imagecreatefromgif(), 0);
    xAdvancedInjectFileReference(\imagecreatefromjpeg(), 0);
    xAdvancedInjectFileReference(\imagecreatefromwbmp(), 0);
    xAdvancedInjectFileReference(\imagecreatefromwebp(), 0);
    xAdvancedInjectFileReference(\imagecreatefrompng(), 0);
    xAdvancedInjectFileReference(\imagecreatefromxbm(), 0);
    xAdvancedInjectFileReference(\imagecreatefromxpm(), 0);

    xAdvancedInjectDirectoryReference(\is_dir(), 0);
    xAdvancedInjectDirectoryReference(\dir(), 0);
    xAdvancedInjectDirectoryReference(\chdir(), 0);
    xAdvancedInjectDirectoryReference(\chroot(), 0);
    xAdvancedInjectDirectoryReference(\mkdir(), 0);
    xAdvancedInjectDirectoryReference(\rmdir(), 0);
    xAdvancedInjectDirectoryReference(\opendir(), 0);
    xAdvancedInjectDirectoryReference(\scandir(), 0);
    xAdvancedInjectDirectoryReference(\stream_resolve_include_path(), 0);
    xAdvancedInjectDirectoryReference(\DirectoryIterator::__construct(), 0);
    xAdvancedInjectDirectoryReference(\FilesystemIterator::__construct(), 0);
    xAdvancedInjectDirectoryReference(\RecursiveDirectoryIterator::__construct(), 0);

    xAdvancedCompletion(\date(), 0, 'date_format');
    xAdvancedCompletion(\date_format(), 1, 'date_format');
    xAdvancedCompletion(\date_create_from_format(), 0, 'date_format');
    xAdvancedCompletion(\DateTime::createFromFormat(), 0, 'date_format');
    xAdvancedCompletion(\DateTimeImmutable::createFromFormat(), 0, 'date_format');
    xAdvancedCompletion(\DateTimeInterface::format(), 0, 'date_format');

    xAdvancedCompletion(\strftime(), 0, 'strftime_format');
    xAdvancedCompletion(\gmstrftime(), 0, 'strftime_format');
    xAdvancedCompletion(\strptime(), 1, 'strftime_format');

    xAdvancedCompletion(\strtotime(), 0, 'datetime_token');
    xAdvancedCompletion(\date_create(), 0, 'datetime_token');
    xAdvancedCompletion(\date_modify(), 1, 'datetime_token');
    xAdvancedCompletion(\DateTime::__construct(), 0, 'datetime_token');
    xAdvancedCompletion(\DateTime::modify(), 0, 'datetime_token');
    xAdvancedCompletion(\DateTimeImmutable::__construct(), 0, 'datetime_token');
    xAdvancedCompletion(\DateTimeImmutable::modify(), 0, 'datetime_token');

    xAdvancedCompletion(\fopen(), 1, 'file_mode');
    xAdvancedCompletion(\popen(), 1, 'file_mode');
    xAdvancedCompletion(\SplFileInfo::openFile(), 0, 'file_mode');

    xAdvancedCompletion(\fsockopen(), 0, 'socket_transport');
    xAdvancedCompletion(\stream_socket_client(), 0, 'socket_transport');

    xAdvancedCompletion(\getenv(), 0, 'env_var');
    xAdvancedCompletion(\apache_getenv(), 0, 'env_var');

    xAdvancedCompletion(\ob_start(), 0, 'ob_handler');

    xAdvancedCompletion(\mb_check_encoding(), 1, 'mb_encoding');
    xAdvancedCompletion(\mb_convert_case(), 2, 'mb_encoding');
    xAdvancedCompletion(\mb_convert_encoding(), 1, 'mb_encoding');
    xAdvancedCompletion(\mb_convert_encoding(), 2, 'mb_encoding');
    xAdvancedCompletion(\mb_convert_kana(), 2, 'mb_encoding');
    xAdvancedCompletion(\mb_convert_variables(), 0, 'mb_encoding');
    xAdvancedCompletion(\mb_convert_variables(), 1, 'mb_encoding');
    xAdvancedCompletion(\mb_decode_numericentity(), 2, 'mb_encoding');
    xAdvancedCompletion(\mb_detect_encoding(), 1, 'mb_encoding');
    xAdvancedCompletion(\mb_detect_order(), 0, 'mb_encoding');
    xAdvancedCompletion(\mb_encode_mimeheader(), 1, 'mb_encoding');
    xAdvancedCompletion(\mb_encode_numericentity(), 2, 'mb_encoding');
    xAdvancedCompletion(\mb_encoding_aliases(), 0, 'mb_encoding');
    xAdvancedCompletion(\mb_http_output(), 0, 'mb_encoding');
    xAdvancedCompletion(\mb_internal_encoding(), 0, 'mb_encoding');
    xAdvancedCompletion(\mb_preferred_mime_name(), 0, 'mb_encoding');
    xAdvancedCompletion(\mb_regex_encoding(), 0, 'mb_encoding');
    xAdvancedCompletion(\mb_strcut(), 3, 'mb_encoding');
    xAdvancedCompletion(\mb_strimwidth(), 4, 'mb_encoding');
    xAdvancedCompletion(\mb_stripos(), 3, 'mb_encoding');
    xAdvancedCompletion(\mb_stristr(), 3, 'mb_encoding');
    xAdvancedCompletion(\mb_strlen(), 1, 'mb_encoding');
    xAdvancedCompletion(\mb_strpos(), 3, 'mb_encoding');
    xAdvancedCompletion(\mb_strrchr(), 3, 'mb_encoding');
    xAdvancedCompletion(\mb_strrichr(), 3, 'mb_encoding');
    xAdvancedCompletion(\mb_strripos(), 3, 'mb_encoding');
    xAdvancedCompletion(\mb_strrpos(), 3, 'mb_encoding');
    xAdvancedCompletion(\mb_strstr(), 3, 'mb_encoding');
    xAdvancedCompletion(\mb_strtolower(), 1, 'mb_encoding');
    xAdvancedCompletion(\mb_strtoupper(), 1, 'mb_encoding');
    xAdvancedCompletion(\mb_strwidth(), 1, 'mb_encoding');
    xAdvancedCompletion(\mb_substr_count(), 2, 'mb_encoding');
    xAdvancedCompletion(\mb_substr(), 3, 'mb_encoding');

    xAdvancedCompletion(\mb_get_info(), 0, 'mb_info_type');
    xAdvancedCompletion(\mb_language(), 0, 'mb_language');

    //xAdvancedCompletion(\htmlentities(), 2, 'html_charset');
    //xAdvancedCompletion(\htmlspecialchars(), 2, 'html_charset');

    //xAdvancedCompletion(\extension_loaded(), 0, 'php_extension');
    xAdvancedCompletion(\get_extension_funcs(), 0, 'php_extension');

    xAdvancedCompletion(\pack(), 0, 'pack_format_code');
    xAdvancedCompletion(\unpack(), 0, 'unpack_format_code');

    xAdvancedCompletion(\printf(), 0, 'format_spec');
    xAdvancedCompletion(\sprintf(), 0, 'format_spec');
    xAdvancedCompletion(\fprintf(), 1, 'format_spec');
    xAdvancedCompletion(\vprintf(), 0, 'format_spec');
    xAdvancedCompletion(\vsprintf(), 0, 'format_spec');
    xAdvancedCompletion(\vfprintf(), 1, 'format_spec');

    xAdvancedCompletion(\sscanf(), 1, 'scan_format_spec');
    xAdvancedCompletion(\fscanf(), 1, 'scan_format_spec');

    xAdvancedCompletion(\header(), 0, 'http_response_header_string');
    xAdvancedCompletion(\header_remove(), 0, 'http_response_header_name');
    xAdvancedCompletion(\http_match_request_header(), 0, 'http_request_header_name');

    xAdvancedCompletion(\PHPSTORM_META\argumentsSet(), 0, 'phpstorm_meta_arguments_set');

}