<?php
namespace PHPSTORM_META {

    /**
     * Adds injection of project file reference in string literals for specified method or function.
     *
     * @param mixed       $functionReference Method or function reference, like: \FQN\Foo::bar()
     * @param int         $argumentIndex     Zero-based index of argument
     * @param string|null $relativeMode      Either '/' (= from top level), '.' (= from current dir) or '' (auto)
     * @return string
     */
    function xAdvancedInjectFileReference($functionReference, $argumentIndex, $relativeMode = null) {
        return "xAdvancedInjectFileReference " . $functionReference . " at " . $argumentIndex . ": " . $relativeMode;
    }

    /**
     * Adds injection of project folder reference in string literals for specified method or function.
     *
     * @param mixed       $functionReference Method or function reference, like: \FQN\Foo::bar()
     * @param int         $argumentIndex     Zero-based index of argument
     * @param string|null $relativeMode      Either '/' (= from top level), '.' (= from current dir) or '' (auto)
     * @return string
     */
    function xAdvancedInjectDirectoryReference($functionReference, $argumentIndex, $relativeMode = null) {
        return "xAdvancedInjectDirectoryReference " . $functionReference . " at " . $argumentIndex . ": " . $relativeMode;
    }

    registerArgumentsSet('x_advanced_fileref_relative_modes', '', '/', '.');
    expectedArguments(\PHPSTORM_META\xAdvancedInjectFileReference(), 2, argumentsSet('x_advanced_fileref_relative_modes'));
    expectedArguments(\PHPSTORM_META\xAdvancedInjectDirectoryReference(), 2, argumentsSet('x_advanced_fileref_relative_modes'));

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
}