package net.king2500.plugins.PhpAdvancedAutoComplete;

import java.util.HashMap;

/**
 * @author Thomas Schulz <mail@king2500.net>
 */
public class PhpCompletionTokens {

    public static String[] httpHeaderResponseFields = {
        "Accept-Ranges",
        "Accept-CH",
        "Accept-CH-Lifetime",
        "Access-Control-Allow-Credentials",
        "Access-Control-Allow-Headers",
        "Access-Control-Allow-Methods",
        "Access-Control-Allow-Origin",
        "Access-Control-Expose-Headers",
        "Access-Control-Max-Age",
        "Age",
        "Allow",
        "Cache-Control",
        "Clear-Site-Data",
        "Connection",
        "Content-DPR",
        "Content-Encoding",
        "Content-Language",
        "Content-Length",
        "Content-Location",
        "Content-MD5",
        "Content-Disposition",
        "Content-Range",
        "Content-Security-Policy",
        "Content-Security-Policy-Report-Only",
        "Content-Type",
        "Cross-Origin-Resource-Policy",
        "Date",
        "ETag",
        "Expect-CT",
        "Expires",
        "Feature-Policy",
        "Keep-Alive",
        "Large-Allocation",
        "Last-Modified",
        "Link",
        "Location",
        "P3P",
        "Pragma",
        "Proxy-Authenticate",
        "Public-Key-Pins",
        "Public-Key-Pins-Report-Only",
        "Referrer-Policy",
        "Refresh",
        "Retry-After",
        "Server",
        "Server-Timing",
        "Set-Cookie",
        "SourceMap",
        "Status",
        "Strict-Transport-Security",
        "Timing-Allow-Origin",
        "Tk",
        "Trailer",
        "Transfer-Encoding",
        "Vary",
        "Via",
        "Warning",
        "WWW-Authenticate",
        "X-Content-Security-Policy",
        "X-Content-Type-Options",
        "X-DNS-Prefetch-Control",
        "X-Frame-Options",
        "X-Powered-By",
        "X-Robots-Tag",
        "X-SourceMap",
        "X-UA-Compatible",
        "X-WebKit-CSP",
        "X-XSS-Protection",
        "HTTP/1.0",
        "HTTP/1.1",
    };

    public static String[] httpHeaderRequestFields = {
        "Accept",
        "Accept-Charset",
        "Accept-Encoding",
        "Accept-Language",
        "Access-Control-Request-Headers",
        "Access-Control-Request-Method",
        "Authorization",
        "Cache-Control",
        "Connection",
        "Content-Disposition",
        "Content-Encoding",
        "Content-Language",
        "Content-Length",
        "Content-Type",
        "Cookie",
        "Date",
        "DNT",
        "Downlink",
        "DPR",
        "Early-Data",
        "Expect",
        "Forwarded",
        "From",
        "Front-End-Https",
        "Host",
        "If-Match",
        "If-Modified-Since",
        "If-None-Match",
        "If-Range",
        "If-Unmodified-Since",
        "Keep-Alive",
        "Max-Forwards",
        "Origin",
        "Pragma",
        "Proxy-Authorization",
        "Proxy-Connection",
        "Range",
        "Referer",
        "Save-Data",
        "TE",
        "Upgrade-Insecure-Requests",
        "User-Agent",
        "Via",
        "Viewport-Width",
        "Warning",
        "Width",
        "X-Forwarded-For",
        "X-Forwarded-Host",
        "X-Forwarded-Proto",
        "X-Powered-By",
        "X-Requested-With",
    };

    public final static HashMap<String, String> httpHeaderResponseFieldsSyntax = new HashMap<String, String>() {{
        put("Accept-Ranges", ": bytes | none");
        put("Accept-CH", ": <field>, <field>, ...");
        put("Accept-CH-Lifetime", ": <delta-seconds>");
        put("Access-Control-Allow-Credentials", ": true");
        put("Access-Control-Allow-Headers", ": <header>, <header>, ...");
        put("Access-Control-Allow-Methods", ": <method>, <method>, ...");
        put("Access-Control-Allow-Origin", ": * | <origin> | null");
        put("Access-Control-Expose-Headers", ": <header>, <header>, ...");
        put("Access-Control-Max-Age", ": <delta-seconds>");
        put("Age", ": <delta-seconds>");
        put("Allow", ": <method>, <method>, ...");
        put("Cache-Control", ": <directive>, <directive>, ...");
        put("Clear-Site-Data", ": \"*\" | \"<directive>\", \"<directive>\", ...");
        put("Connection", ": close");
        put("Content-DPR", ": <pixel-ratio>");
        put("Content-Encoding", ": <encoding>, <encoding>, ...");
        put("Content-Language", ": <language-tag>, <language-tag>, ...");
        put("Content-Length", ": <length>");
        put("Content-Location", ": <url>");
        put("Content-MD5", ": <base64-encoded-checksum>");
        put("Content-Disposition", ": inline | attachment[; filename=\"name.ext\"]");
        put("Content-Range", ": <unit> <range-start>-<range-end>/<size>");
        put("Content-Security-Policy", ": <policy-directive>; <policy-directive>; ...");
        put("Content-Security-Policy-Report-Only", ": <policy-directive>; <policy-directive>; ...");
        put("Content-Type", ": <mime-type>[; charset=<charset>]");
        put("Cross-Origin-Resource-Policy", ": same-site | same-origin");
        put("Date", ": <day-name>, <day> <month> <year> <hour>:<minute>:<second> GMT");
        put("ETag", ": [W/]\"<etag-value>\"");
        put("Expect-CT", ": <directive>, <directive>, ...");
        put("Expires", ": <http-date>");
        put("Feature-Policy", ": <directive> <allowlist>; <directive> <allowlist>; ...");
        put("Keep-Alive", ": <parameters>, <parameters>, ...");
        put("Large-Allocation", ": 0 | <megabytes>");
        put("Last-Modified", ": <day-name>, <day> <month> <year> <hour>:<minute>:<second> GMT");
        put("Link", ": <<link-uri>>; rel=\"<param>\"");
        put("Location", ": <url>");
        put("P3P", ": CP=\"<policy>\"");
        put("Pragma", ": no-cache");
        put("Proxy-Authenticate", ": <type> realm=<realm>");
        put("Public-Key-Pins", ": <directive>; <directive>; ...");
        put("Public-Key-Pins-Report-Only", ": <directive>; <directive>; ...");
        put("Referrer-Policy", ": <policy>");
        put("Refresh", ": <seconds>; url=<url>");
        put("Retry-After", ": <http-date> | <delay-seconds>");
        put("Server", ": <product>");
        put("Server-Timing", ": <metric>[;<param>=<val>], <metric>, ...");
        put("Set-Cookie", ": <cookie-name>=<cookie-value>[; <param>=<val>][; <param>] ...");
        put("SourceMap", ": <url>");
        put("Status", ": <status-code> <message>");
        put("Strict-Transport-Security", ": max-age=<expire-time>[; includeSubDomains][; preload]");
        put("Timing-Allow-Origin", ": * | <origin>, <origin>, ...");
        put("Tk", ": <directive>");
        put("Trailer", ": <header>, <header>, ...");
        put("Transfer-Encoding", ": <encoding>, <encoding>, ...");
        put("Vary", ": * | <header-name>, <header-name>, ...");
        put("Via", ": [<protocol-name>/]<protocol-version> <host>[:<port>], ...");
        put("Warning", ": <warn-code> <warn-agent> <warn-text> [<warn-date>]");
        put("WWW-Authenticate", ": <type> realm=<realm>");
        put("X-Content-Security-Policy", ": <policy-directive>; <policy-directive>; ...");
        put("X-Content-Type-Options", ": nosniff");
        put("X-DNS-Prefetch-Control", ": on | off");
        put("X-Frame-Options", ": deny | sameorigin | allow-from <url>");
        put("X-Powered-By", ": <product>");
        put("X-Robots-Tag", ": all | <directive>, <directive>, ...");
        put("X-SourceMap", ": <url>");
        put("X-UA-Compatible", ": <condition>,<condition>,...");
        put("X-WebKit-CSP", ": <policy-directive>; <policy-directive>; ...");
        put("X-XSS-Protection", ": 0 | 1 | 1; mode=block | 1; report=<reporting-URI>");
        put("HTTP/1.0", " <status-code> <message>");
        put("HTTP/1.1", " <status-code> <message>");
    }};

    public static String[] httpHeaderDeprecatedFields = {
        "Pragma", "Status", "X-SourceMap",
    };

    public static String[] httpHeaderResponseFieldsNotInTrailer = {
        "Transfer-Encoding", "Content-Length", "Host", "Cache-Control", "Max-Forwards", "TE", "Authorization", "Set-Cookie", "Content-Encoding", "Content-Type", "Content-Range", "Trailer", "HTTP/1.0", "HTTP/1.1",
    };

    public static String[] httpMethods = { "GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "OPTIONS", "CONNECT", "PATCH" };
    public static String[] httpRangeTypes = { "bytes", "none" };
    public static String[] httpACAllowCredentials = { "true" };
    public static String[] httpACAllowHeadersAlways = { "Accept", "Accept-Language", "Content-Language", "Content-Type" };
    public static String[] httpACAllowOrigin = { "*", "https://", "null" };
    public static String[] httpCacheControlDirectives = { "public", "private", "must-revalidate", "no-cache", "no-store", "no-transform", "proxy-revalidate", "max-age=", "s-maxage=", "immutable", "stale-while-revalidate=", "stale-if-error=" };
    public static String[] httpClearSiteDataDirectives = { "\"cache\"", "\"cookies\"", "\"storage\"", "\"executionContexts\"", "\"*\"" };
    public static String[] httpClientHintDirectives = { "Downlink", "DPR", "Viewport-Width", "Width" };
    public static String[] httpConnectionOptions = { "close" };
    public static String[] httpEncodingTokens = { "gzip", "compress", "deflate", "identity", "br" };
    public static String[] isoLanguageCodes = { "aa", "ab", "af", "am", "ar-AE", "ar-BH", "ar-DZ", "ar-EG", "ar-IQ", "ar-JO", "ar-KW", "ar-LB", "ar-LY", "ar-MA", "ar-OM", "ar-QA", "ar-SA", "ar-SY", "ar-TN", "ar-YE", "ar", "as", "ay", "az", "ba", "be", "bg", "bh", "bi", "bn", "bo", "br", "ca", "cel-gaulish", "co", "cs", "cy", "da", "de-AT", "de-CH", "de-DE", "de-LI", "de-LU", "de", "div", "dz", "el", "en-AU", "en-BZ", "en-CA", "en-GB", "en-IE", "en-JM", "en-NZ", "en-PH", "en-TT", "en-US", "en-ZA", "en-ZW", "en", "eo", "es-AR", "es-BO", "es-CL", "es-CO", "es-CR", "es-DO", "es-EC", "es-ES", "es-GT", "es-HN", "es-MX", "es-NI", "es-PA", "es-PE", "es-PR", "es-PY", "es-SV", "es-US", "es-UY", "es-VE", "es", "et", "eu", "fa", "fi", "fj", "fo", "fr-BE", "fr-CA", "fr-CH", "fr-FR", "fr-LU", "fr-MC", "fr", "fy", "ga", "gd", "gl", "gn", "gu", "ha", "he", "hi", "hr", "hu", "hy", "ia", "id", "ie", "ik", "is", "it-CH", "it-IT", "it", "iu", "iw", "ja", "ji", "jv", "ka", "kk", "kl", "km", "kn", "ko", "kok", "ks", "ku", "ky", "kz", "la", "ln", "lo", "lt", "lv", "mg", "mi", "mk", "ml", "mn", "mo", "mr", "ms", "mt", "my", "na", "nb-NO", "ne", "nl-BE", "nl-NL", "nl", "nn-NO", "no-NO", "no", "oc", "om", "or", "pa", "pl", "ps", "pt-BR", "pt-PT", "pt", "qu", "rm", "rn", "ro-MD", "ro-RO", "ro", "ru-MD", "ru-RU", "ru", "rw", "sa", "sb", "sd", "sg", "sh", "si", "sk", "sl", "sm", "sn", "so", "sq", "sr", "ss", "st", "su", "sv-FI", "sv-SE", "sv", "sw", "sx", "syr", "ta", "te", "tg", "th", "ti", "tk", "tl", "tn", "to", "tr", "ts", "tt", "tw", "ug", "uk", "ur", "uz", "vi", "vo", "wo", "xh", "yi", "yo", "za", "zh-CN", "zh-HK", "zh-MO", "zh-SG", "zh-TW", "zh-guoyu", "zh-min-nan", "zh-xiang", "zh", "zu", "x-elmer", "x-hacker", "x-klingon", "x-pig-latin", "x-pirate" };
    public static String[] httpContentDispositionTokens = { "inline", "attachment", "attachment; filename=" };
    public static String[] mimeTypes = { "application/1d-interleaved-parityfec", "application/3gpp-ims+xml", "application/activemessage", "application/andrew-inset", "application/applefile", "application/atom+xml", "application/atomdeleted+xml", "application/atomicmail", "application/atomcat+xml", "application/atomsvc+xml", "application/auth-policy+xml", "application/batch-SMTP", "application/beep+xml", "application/calendar+xml", "application/call-completion", "application/cals-1840", "application/ccmp+xml", "application/ccxml+xml", "application/cdmi-capability", "application/cdmi-container", "application/cdmi-domain", "application/cdmi-object", "application/cdmi-queue", "application/cea-2018+xml", "application/cellml+xml", "application/cfw", "application/cnrp+xml", "application/commonground", "application/conference-info+xml", "application/cpl+xml", "application/csta+xml", "application/CSTAdata+xml", "application/cybercash", "application/dash+xml", "application/davmount+xml", "application/dca-rft", "application/dec-dx", "application/dialog-info+xml", "application/dicom", "application/dns", "application/dskpp+xml", "application/dssc+der", "application/dssc+xml", "application/dvcs", "application/ecmascript", "application/EDI-Consent", "application/EDIFACT", "application/EDI-X12", "application/emma+xml", "application/encaprtp", "application/epp+xml", "application/eshop", "application/example", "application/exi", "application/fastinfoset", "application/fastsoap", "application/fdt+xml", "application/fits", "application/font-sfnt", "application/font-tdpfr", "application/font-woff", "application/framework-attributes+xml", "application/gzip", "application/H224", "application/held+xml", "application/http", "application/hyperstudio", "application/ibe-key-request+xml", "application/ibe-pkg-reply+xml", "application/ibe-pp-data", "application/iges", "application/im-iscomposing+xml", "application/index", "application/index.cmd", "application/index.obj", "application/index.response", "application/index.vnd", "application/inkml+xml", "application/iotp", "application/ipfix", "application/ipp", "application/isup", "application/javascript", "application/json", "application/json-patch+json", "application/kpml-request+xml", "application/kpml-response+xml", "application/link-format", "application/lost+xml", "application/lostsync+xml", "application/mac-binhex40", "application/macwriteii", "application/mads+xml", "application/marc", "application/marcxml+xml", "application/mathematica", "application/mathml-content+xml", "application/mathml-presentation+xml", "application/mathml+xml", "application/mbms-associated-procedure-description+xml", "application/mbms-deregister+xml", "application/mbms-envelope+xml", "application/mbms-msk-response+xml", "application/mbms-msk+xml", "application/mbms-protection-description+xml", "application/mbms-reception-report+xml", "application/mbms-register-response+xml", "application/mbms-register+xml", "application/mbms-user-service-description+xml", "application/mbox", "application/media_control+xml", "application/media-policy-dataset+xml", "application/mediaservercontrol+xml", "application/metalink4+xml", "application/mets+xml", "application/mikey", "application/mods+xml", "application/moss-keys", "application/moss-signature", "application/mosskey-data", "application/mosskey-request", "application/mp21", "application/mp4", "application/mpeg4-generic", "application/mpeg4-iod", "application/mpeg4-iod-xmt", "application/mrb-consumer+xml", "application/mrb-publish+xml", "application/msc-ivr+xml", "application/msc-mixer+xml", "application/msword", "application/mxf", "application/nasdata", "application/news-checkgroups", "application/news-groupinfo", "application/news-transmission", "application/nlsml+xml", "application/nss", "application/ocsp-request", "application/ocsp-response", "application/octet-stream", "application/oda", "application/oebps-package+xml", "application/ogg", "application/oxps", "application/p2p-overlay+xml", "application/parityfec", "application/patch-ops-error+xml", "application/pdf", "application/pgp-encrypted", "application/pgp-keys", "application/pgp-signature", "application/pidf+xml", "application/pidf-diff+xml", "application/pkcs10", "application/pkcs7-mime", "application/pkcs7-signature", "application/pkcs8", "application/pkix-attr-cert", "application/pkix-cert", "application/pkixcmp", "application/pkix-crl", "application/pkix-pkipath", "application/pls+xml", "application/poc-settings+xml", "application/postscript", "application/provenance+xml", "application/prs.alvestrand.titrax-sheet", "application/prs.cww", "application/prs.nprend", "application/prs.plucker", "application/prs.rdf-xml-crypt", "application/prs.xsf+xml", "application/pskc+xml", "application/rdf+xml", "application/qsig", "application/raptorfec", "application/reginfo+xml", "application/relax-ng-compact-syntax", "application/remote-printing", "application/resource-lists-diff+xml", "application/resource-lists+xml", "application/riscos", "application/rlmi+xml", "application/rls-services+xml", "application/rpki-ghostbusters", "application/rpki-manifest", "application/rpki-roa", "application/rpki-updown", "application/rtf", "application/rtploopback", "application/rtx", "application/samlassertion+xml", "application/samlmetadata+xml", "application/sbml+xml", "application/scvp-cv-request", "application/scvp-cv-response", "application/scvp-vp-request", "application/scvp-vp-response", "application/sdp", "application/sep-exi", "application/sep+xml", "application/session-info", "application/set-payment", "application/set-payment-initiation", "application/set-registration", "application/set-registration-initiation", "application/sgml", "application/sgml-open-catalog", "application/shf+xml", "application/sieve", "application/simple-filter+xml", "application/simple-message-summary", "application/simpleSymbolContainer", "application/slate", "application/smil (OBSOLETE)", "application/smil+xml", "application/smpte336m", "application/soap+fastinfoset", "application/soap+xml", "application/sparql-query", "application/sparql-results+xml", "application/spirits-event+xml", "application/sql", "application/srgs", "application/srgs+xml", "application/sru+xml", "application/ssml+xml", "application/tamp-apex-update", "application/tamp-apex-update-confirm", "application/tamp-community-update", "application/tamp-community-update-confirm", "application/tamp-error", "application/tamp-sequence-adjust", "application/tamp-sequence-adjust-confirm", "application/tamp-status-query", "application/tamp-status-response", "application/tamp-update", "application/tamp-update-confirm", "application/tei+xml", "application/thraud+xml", "application/timestamp-query", "application/timestamp-reply", "application/timestamped-data", "application/tve-trigger", "application/ulpfec", "application/urc-grpsheet+xml", "application/urc-ressheet+xml", "application/urc-targetdesc+xml", "application/urc-uisocketdesc+xml", "application/vcard+xml", "application/vemmi", "application/vnd.3gpp.bsf+xml", "application/vnd.3gpp.pic-bw-large", "application/vnd.3gpp.pic-bw-small", "application/vnd.3gpp.pic-bw-var", "application/vnd.3gpp.sms", "application/vnd.3gpp2.bcmcsinfo+xml", "application/vnd.3gpp2.sms", "application/vnd.3gpp2.tcap", "application/vnd.3M.Post-it-Notes", "application/vnd.accpac.simply.aso", "application/vnd.accpac.simply.imp", "application/vnd.acucobol", "application/vnd.acucorp", "application/vnd.adobe.formscentral.fcdt", "application/vnd.adobe.fxp", "application/vnd.adobe.partial-upload", "application/vnd.adobe.xdp+xml", "application/vnd.adobe.xfdf", "application/vnd.aether.imp", "application/vnd.ah-barcode", "application/vnd.ahead.space", "application/vnd.airzip.filesecure.azf", "application/vnd.airzip.filesecure.azs", "application/vnd.americandynamics.acc", "application/vnd.amiga.ami", "application/vnd.amundsen.maze+xml", "application/vnd.anser-web-certificate-issue-initiation", "application/vnd.antix.game-component", "application/vnd.apple.mpegurl", "application/vnd.apple.installer+xml", "application/vnd.aristanetworks.swi", "application/vnd.astraea-software.iota", "application/vnd.audiograph", "application/vnd.autopackage", "application/vnd.avistar+xml", "application/vnd.balsamiq.bmml+xml", "application/vnd.blueice.multipass", "application/vnd.bluetooth.ep.oob", "application/vnd.bmi", "application/vnd.businessobjects", "application/vnd.cab-jscript", "application/vnd.canon-cpdl", "application/vnd.canon-lips", "application/vnd.cendio.thinlinc.clientconf", "application/vnd.century-systems.tcp_stream", "application/vnd.chemdraw+xml", "application/vnd.chipnuts.karaoke-mmd", "application/vnd.cinderella", "application/vnd.cirpack.isdn-ext", "application/vnd.claymore", "application/vnd.cloanto.rp9", "application/vnd.clonk.c4group", "application/vnd.cluetrust.cartomobile-config", "application/vnd.cluetrust.cartomobile-config-pkg", "application/vnd.collection+json", "application/vnd.collection.next+json", "application/vnd.commerce-battelle", "application/vnd.commonspace", "application/vnd.cosmocaller", "application/vnd.contact.cmsg", "application/vnd.crick.clicker", "application/vnd.crick.clicker.keyboard", "application/vnd.crick.clicker.palette", "application/vnd.crick.clicker.template", "application/vnd.crick.clicker.wordbank", "application/vnd.criticaltools.wbs+xml", "application/vnd.ctc-posml", "application/vnd.ctct.ws+xml", "application/vnd.cups-pdf", "application/vnd.cups-postscript", "application/vnd.cups-ppd", "application/vnd.cups-raster", "application/vnd.cups-raw", "application/vnd.curl", "application/vnd.cyan.dean.root+xml", "application/vnd.cybank", "application/vnd.dart", "application/vnd.data-vision.rdz", "application/vnd.dece.data", "application/vnd.dece.ttml+xml", "application/vnd.dece.unspecified", "application/vnd.dece.zip", "application/vnd.denovo.fcselayout-link", "application/vnd.desmume.movie", "application/vnd.dir-bi.plate-dl-nosuffix", "application/vnd.dm.delegation+xml", "application/vnd.dna", "application/vnd.dolby.mobile.1", "application/vnd.dolby.mobile.2", "application/vnd.dpgraph", "application/vnd.dreamfactory", "application/vnd.dtg.local", "application/vnd.dtg.local.flash", "application/vnd.dtg.local.html", "application/vnd.dvb.ait", "application/vnd.dvb.dvbj", "application/vnd.dvb.esgcontainer", "application/vnd.dvb.ipdcdftnotifaccess", "application/vnd.dvb.ipdcesgaccess", "application/vnd.dvb.ipdcesgaccess2", "application/vnd.dvb.ipdcesgpdd", "application/vnd.dvb.ipdcroaming", "application/vnd.dvb.iptv.alfec-base", "application/vnd.dvb.iptv.alfec-enhancement", "application/vnd.dvb.notif-aggregate-root+xml", "application/vnd.dvb.notif-container+xml", "application/vnd.dvb.notif-generic+xml", "application/vnd.dvb.notif-ia-msglist+xml", "application/vnd.dvb.notif-ia-registration-request+xml", "application/vnd.dvb.notif-ia-registration-response+xml", "application/vnd.dvb.notif-init+xml", "application/vnd.dvb.pfr", "application/vnd.dvb.service", "application/vnd.dxr", "application/vnd.dynageo", "application/vnd.easykaraoke.cdgdownload", "application/vnd.ecdis-update", "application/vnd.ecowin.chart", "application/vnd.ecowin.filerequest", "application/vnd.ecowin.fileupdate", "application/vnd.ecowin.series", "application/vnd.ecowin.seriesrequest", "application/vnd.ecowin.seriesupdate", "application/vnd.emclient.accessrequest+xml", "application/vnd.enliven", "application/vnd.eprints.data+xml", "application/vnd.epson.esf", "application/vnd.epson.msf", "application/vnd.epson.quickanime", "application/vnd.epson.salt", "application/vnd.epson.ssf", "application/vnd.ericsson.quickcall", "application/vnd.eszigno3+xml", "application/vnd.etsi.aoc+xml", "application/vnd.etsi.cug+xml", "application/vnd.etsi.iptvcommand+xml", "application/vnd.etsi.iptvdiscovery+xml", "application/vnd.etsi.iptvprofile+xml", "application/vnd.etsi.iptvsad-bc+xml", "application/vnd.etsi.iptvsad-cod+xml", "application/vnd.etsi.iptvsad-npvr+xml", "application/vnd.etsi.iptvservice+xml", "application/vnd.etsi.iptvsync+xml", "application/vnd.etsi.iptvueprofile+xml", "application/vnd.etsi.mcid+xml", "application/vnd.etsi.mheg5", "application/vnd.etsi.overload-control-policy-dataset+xml", "application/vnd.etsi.pstn+xml", "application/vnd.etsi.sci+xml", "application/vnd.etsi.simservs+xml", "application/vnd.etsi.tsl+xml", "application/vnd.etsi.tsl.der", "application/vnd.eudora.data", "application/vnd.ezpix-album", "application/vnd.ezpix-package", "application/vnd.f-secure.mobile", "application/vnd.fdf", "application/vnd.fdsn.mseed", "application/vnd.fdsn.seed", "application/vnd.ffsns", "application/vnd.fints", "application/vnd.FloGraphIt", "application/vnd.fluxtime.clip", "application/vnd.font-fontforge-sfd", "application/vnd.framemaker", "application/vnd.frogans.fnc", "application/vnd.frogans.ltf", "application/vnd.fsc.weblaunch", "application/vnd.fujitsu.oasys", "application/vnd.fujitsu.oasys2", "application/vnd.fujitsu.oasys3", "application/vnd.fujitsu.oasysgp", "application/vnd.fujitsu.oasysprs", "application/vnd.fujixerox.ART4", "application/vnd.fujixerox.ART-EX", "application/vnd.fujixerox.ddd", "application/vnd.fujixerox.docuworks", "application/vnd.fujixerox.docuworks.binder", "application/vnd.fujixerox.docuworks.container", "application/vnd.fujixerox.HBPL", "application/vnd.fut-misnet", "application/vnd.fuzzysheet", "application/vnd.genomatix.tuxedo", "application/vnd.geogebra.file", "application/vnd.geogebra.tool", "application/vnd.geometry-explorer", "application/vnd.geonext", "application/vnd.geoplan", "application/vnd.geospace", "application/vnd.globalplatform.card-content-mgt", "application/vnd.globalplatform.card-content-mgt-response", "application/vnd.google-earth.kml+xml", "application/vnd.google-earth.kmz", "application/vnd.grafeq", "application/vnd.gridmp", "application/vnd.groove-account", "application/vnd.groove-help", "application/vnd.groove-identity-message", "application/vnd.groove-injector", "application/vnd.groove-tool-message", "application/vnd.groove-tool-template", "application/vnd.groove-vcard", "application/vnd.hal+json", "application/vnd.hal+xml", "application/vnd.HandHeld-Entertainment+xml", "application/vnd.hbci", "application/vnd.hcl-bireports", "application/vnd.hhe.lesson-player", "application/vnd.hp-HPGL", "application/vnd.hp-hpid", "application/vnd.hp-hps", "application/vnd.hp-jlyt", "application/vnd.hp-PCL", "application/vnd.hp-PCLXL", "application/vnd.httphone", "application/vnd.hydrostatix.sof-data", "application/vnd.hzn-3d-crossword", "application/vnd.ibm.afplinedata", "application/vnd.ibm.electronic-media", "application/vnd.ibm.MiniPay", "application/vnd.ibm.modcap", "application/vnd.ibm.rights-management", "application/vnd.ibm.secure-container", "application/vnd.iccprofile", "application/vnd.ieee.1905", "application/vnd.igloader", "application/vnd.immervision-ivp", "application/vnd.immervision-ivu", "application/vnd.informedcontrol.rms+xml", "application/vnd.infotech.project", "application/vnd.infotech.project+xml", "application/vnd.informix-visionary", "application/vnd.innopath.wamp.notification", "application/vnd.insors.igm", "application/vnd.intercon.formnet", "application/vnd.intergeo", "application/vnd.intertrust.digibox", "application/vnd.intertrust.nncp", "application/vnd.intu.qbo", "application/vnd.intu.qfx", "application/vnd.iptc.g2.conceptitem+xml", "application/vnd.iptc.g2.knowledgeitem+xml", "application/vnd.iptc.g2.newsitem+xml", "application/vnd.iptc.g2.newsmessage+xml", "application/vnd.iptc.g2.packageitem+xml", "application/vnd.iptc.g2.planningitem+xml", "application/vnd.ipunplugged.rcprofile", "application/vnd.irepository.package+xml", "application/vnd.is-xpr", "application/vnd.isac.fcs", "application/vnd.jam", "application/vnd.japannet-directory-service", "application/vnd.japannet-jpnstore-wakeup", "application/vnd.japannet-payment-wakeup", "application/vnd.japannet-registration", "application/vnd.japannet-registration-wakeup", "application/vnd.japannet-setstore-wakeup", "application/vnd.japannet-verification", "application/vnd.japannet-verification-wakeup", "application/vnd.jcp.javame.midlet-rms", "application/vnd.jisp", "application/vnd.joost.joda-archive", "application/vnd.jsk.isdn-ngn", "application/vnd.kahootz", "application/vnd.kde.karbon", "application/vnd.kde.kchart", "application/vnd.kde.kformula", "application/vnd.kde.kivio", "application/vnd.kde.kontour", "application/vnd.kde.kpresenter", "application/vnd.kde.kspread", "application/vnd.kde.kword", "application/vnd.kenameaapp", "application/vnd.kidspiration", "application/vnd.Kinar", "application/vnd.koan", "application/vnd.kodak-descriptor", "application/vnd.las.las+xml", "application/vnd.liberty-request+xml", "application/vnd.llamagraphics.life-balance.desktop", "application/vnd.llamagraphics.life-balance.exchange+xml", "application/vnd.lotus-1-2-3", "application/vnd.lotus-approach", "application/vnd.lotus-freelance", "application/vnd.lotus-notes", "application/vnd.lotus-organizer", "application/vnd.lotus-screencam", "application/vnd.lotus-wordpro", "application/vnd.macports.portpkg", "application/vnd.marlin.drm.actiontoken+xml", "application/vnd.marlin.drm.conftoken+xml", "application/vnd.marlin.drm.license+xml", "application/vnd.marlin.drm.mdcf", "application/vnd.mcd", "application/vnd.medcalcdata", "application/vnd.mediastation.cdkey", "application/vnd.meridian-slingshot", "application/vnd.MFER", "application/vnd.mfmp", "application/vnd.micrografx.flo", "application/vnd.micrografx.igx", "application/vnd.mif", "application/vnd.minisoft-hp3000-save", "application/vnd.mitsubishi.misty-guard.trustweb", "application/vnd.Mobius.DAF", "application/vnd.Mobius.DIS", "application/vnd.Mobius.MBK", "application/vnd.Mobius.MQY", "application/vnd.Mobius.MSL", "application/vnd.Mobius.PLC", "application/vnd.Mobius.TXF", "application/vnd.mophun.application", "application/vnd.mophun.certificate", "application/vnd.motorola.flexsuite", "application/vnd.motorola.flexsuite.adsi", "application/vnd.motorola.flexsuite.fis", "application/vnd.motorola.flexsuite.gotap", "application/vnd.motorola.flexsuite.kmr", "application/vnd.motorola.flexsuite.ttc", "application/vnd.motorola.flexsuite.wem", "application/vnd.motorola.iprm", "application/vnd.mozilla.xul+xml", "application/vnd.ms-artgalry", "application/vnd.ms-asf", "application/vnd.ms-cab-compressed", "application/vnd.mseq", "application/vnd.ms-excel", "application/vnd.ms-excel.addin.macroEnabled.12", "application/vnd.ms-excel.sheet.binary.macroEnabled.12", "application/vnd.ms-excel.sheet.macroEnabled.12", "application/vnd.ms-excel.template.macroEnabled.12", "application/vnd.ms-fontobject", "application/vnd.ms-htmlhelp", "application/vnd.ms-ims", "application/vnd.ms-lrm", "application/vnd.ms-office.activeX+xml", "application/vnd.ms-officetheme", "application/vnd.ms-playready.initiator+xml", "application/vnd.ms-powerpoint", "application/vnd.ms-powerpoint.addin.macroEnabled.12", "application/vnd.ms-powerpoint.presentation.macroEnabled.12", "application/vnd.ms-powerpoint.slide.macroEnabled.12", "application/vnd.ms-powerpoint.slideshow.macroEnabled.12", "application/vnd.ms-powerpoint.template.macroEnabled.12", "application/vnd.ms-project", "application/vnd.ms-tnef", "application/vnd.ms-windows.printerpairing", "application/vnd.ms-wmdrm.lic-chlg-req", "application/vnd.ms-wmdrm.lic-resp", "application/vnd.ms-wmdrm.meter-chlg-req", "application/vnd.ms-wmdrm.meter-resp", "application/vnd.ms-word.document.macroEnabled.12", "application/vnd.ms-word.template.macroEnabled.12", "application/vnd.ms-works", "application/vnd.ms-wpl", "application/vnd.ms-xpsdocument", "application/vnd.msign", "application/vnd.multiad.creator", "application/vnd.multiad.creator.cif", "application/vnd.musician", "application/vnd.music-niff", "application/vnd.muvee.style", "application/vnd.mynfc", "application/vnd.ncd.control", "application/vnd.ncd.reference", "application/vnd.nervana", "application/vnd.netfpx", "application/vnd.neurolanguage.nlu", "application/vnd.nintendo.nitro.rom", "application/vnd.nitf", "application/vnd.noblenet-directory", "application/vnd.noblenet-sealer", "application/vnd.noblenet-web", "application/vnd.nokia.catalogs", "application/vnd.nokia.conml+wbxml", "application/vnd.nokia.conml+xml", "application/vnd.nokia.iptv.config+xml", "application/vnd.nokia.iSDS-radio-presets", "application/vnd.nokia.landmark+wbxml", "application/vnd.nokia.landmark+xml", "application/vnd.nokia.landmarkcollection+xml", "application/vnd.nokia.ncd", "application/vnd.nokia.n-gage.ac+xml", "application/vnd.nokia.n-gage.data", "application/vnd.nokia.n-gage.symbian.install", "application/vnd.nokia.pcd+wbxml", "application/vnd.nokia.pcd+xml", "application/vnd.nokia.radio-preset", "application/vnd.nokia.radio-presets", "application/vnd.novadigm.EDM", "application/vnd.novadigm.EDX", "application/vnd.novadigm.EXT", "application/vnd.ntt-local.content-share", "application/vnd.ntt-local.file-transfer", "application/vnd.ntt-local.sip-ta_remote", "application/vnd.ntt-local.sip-ta_tcp_stream", "application/vnd.oasis.opendocument.chart", "application/vnd.oasis.opendocument.chart-template", "application/vnd.oasis.opendocument.database", "application/vnd.oasis.opendocument.formula", "application/vnd.oasis.opendocument.formula-template", "application/vnd.oasis.opendocument.graphics", "application/vnd.oasis.opendocument.graphics-template", "application/vnd.oasis.opendocument.image", "application/vnd.oasis.opendocument.image-template", "application/vnd.oasis.opendocument.presentation", "application/vnd.oasis.opendocument.presentation-template", "application/vnd.oasis.opendocument.spreadsheet", "application/vnd.oasis.opendocument.spreadsheet-template", "application/vnd.oasis.opendocument.text", "application/vnd.oasis.opendocument.text-master", "application/vnd.oasis.opendocument.text-template", "application/vnd.oasis.opendocument.text-web", "application/vnd.obn", "application/vnd.oftn.l10n+json", "application/vnd.oipf.contentaccessdownload+xml", "application/vnd.oipf.contentaccessstreaming+xml", "application/vnd.oipf.cspg-hexbinary", "application/vnd.oipf.dae.svg+xml", "application/vnd.oipf.dae.xhtml+xml", "application/vnd.oipf.mippvcontrolmessage+xml", "application/vnd.oipf.pae.gem", "application/vnd.oipf.spdiscovery+xml", "application/vnd.oipf.spdlist+xml", "application/vnd.oipf.ueprofile+xml", "application/vnd.oipf.userprofile+xml", "application/vnd.olpc-sugar", "application/vnd.oma.bcast.associated-procedure-parameter+xml", "application/vnd.oma.bcast.drm-trigger+xml", "application/vnd.oma.bcast.imd+xml", "application/vnd.oma.bcast.ltkm", "application/vnd.oma.bcast.notification+xml", "application/vnd.oma.bcast.provisioningtrigger", "application/vnd.oma.bcast.sgboot", "application/vnd.oma.bcast.sgdd+xml", "application/vnd.oma.bcast.sgdu", "application/vnd.oma.bcast.simple-symbol-container", "application/vnd.oma.bcast.smartcard-trigger+xml", "application/vnd.oma.bcast.sprov+xml", "application/vnd.oma.bcast.stkm", "application/vnd.oma.cab-address-book+xml", "application/vnd.oma.cab-feature-handler+xml", "application/vnd.oma.cab-pcc+xml", "application/vnd.oma.cab-subs-invite+xml", "application/vnd.oma.cab-user-prefs+xml", "application/vnd.oma.dcd", "application/vnd.oma.dcdc", "application/vnd.oma.dd2+xml", "application/vnd.oma.drm.risd+xml", "application/vnd.oma.group-usage-list+xml", "application/vnd.oma.pal+xml", "application/vnd.oma.poc.detailed-progress-report+xml", "application/vnd.oma.poc.final-report+xml", "application/vnd.oma.poc.groups+xml", "application/vnd.oma.poc.invocation-descriptor+xml", "application/vnd.oma.poc.optimized-progress-report+xml", "application/vnd.oma.push", "application/vnd.oma.scidm.messages+xml", "application/vnd.oma.xcap-directory+xml", "application/vnd.omads-email+xml", "application/vnd.omads-file+xml", "application/vnd.omads-folder+xml", "application/vnd.omaloc-supl-init", "application/vnd.oma-scws-config", "application/vnd.oma-scws-http-request", "application/vnd.oma-scws-http-response", "application/vnd.openofficeorg.extension", "application/vnd.openxmlformats-officedocument.custom-properties+xml", "application/vnd.openxmlformats-officedocument.customXmlProperties+xml", "application/vnd.openxmlformats-officedocument.drawing+xml", "application/vnd.openxmlformats-officedocument.drawingml.chart+xml", "application/vnd.openxmlformats-officedocument.drawingml.chartshapes+xml", "application/vnd.openxmlformats-officedocument.drawingml.diagramColors+xml", "application/vnd.openxmlformats-officedocument.drawingml.diagramData+xml", "application/vnd.openxmlformats-officedocument.drawingml.diagramLayout+xml", "application/vnd.openxmlformats-officedocument.drawingml.diagramStyle+xml", "application/vnd.openxmlformats-officedocument.extended-properties+xml", "application/vnd.openxmlformats-officedocument.presentationml.commentAuthors+xml", "application/vnd.openxmlformats-officedocument.presentationml.comments+xml", "application/vnd.openxmlformats-officedocument.presentationml.handoutMaster+xml", "application/vnd.openxmlformats-officedocument.presentationml.notesMaster+xml", "application/vnd.openxmlformats-officedocument.presentationml.notesSlide+xml", "application/vnd.openxmlformats-officedocument.presentationml.presentation", "application/vnd.openxmlformats-officedocument.presentationml.presentation.main+xml", "application/vnd.openxmlformats-officedocument.presentationml.presProps+xml", "application/vnd.openxmlformats-officedocument.presentationml.slide", "application/vnd.openxmlformats-officedocument.presentationml.slide+xml", "application/vnd.openxmlformats-officedocument.presentationml.slideLayout+xml", "application/vnd.openxmlformats-officedocument.presentationml.slideMaster+xml", "application/vnd.openxmlformats-officedocument.presentationml.slideshow", "application/vnd.openxmlformats-officedocument.presentationml.slideshow.main+xml", "application/vnd.openxmlformats-officedocument.presentationml.slideUpdateInfo+xml", "application/vnd.openxmlformats-officedocument.presentationml.tableStyles+xml", "application/vnd.openxmlformats-officedocument.presentationml.tags+xml", "application/vnd.openxmlformats-officedocument.presentationml.template", "application/vnd.openxmlformats-officedocument.presentationml.template.main+xml", "application/vnd.openxmlformats-officedocument.presentationml.viewProps+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.calcChain+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.chartsheet+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.comments+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.connections+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.dialogsheet+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.externalLink+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.pivotCacheDefinition+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.pivotCacheRecords+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.pivotTable+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.queryTable+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.revisionHeaders+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.revisionLog+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.sharedStrings+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheetMetadata+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.table+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.tableSingleCells+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.template", "application/vnd.openxmlformats-officedocument.spreadsheetml.template.main+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.userNames+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.volatileDependencies+xml", "application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml", "application/vnd.openxmlformats-officedocument.theme+xml", "application/vnd.openxmlformats-officedocument.themeOverride+xml", "application/vnd.openxmlformats-officedocument.vmlDrawing", "application/vnd.openxmlformats-officedocument.wordprocessingml.comments+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/vnd.openxmlformats-officedocument.wordprocessingml.document.glossary+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.endnotes+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.fontTable+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.footer+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.footnotes+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.numbering+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.template", "application/vnd.openxmlformats-officedocument.wordprocessingml.template.main+xml", "application/vnd.openxmlformats-officedocument.wordprocessingml.webSettings+xml", "application/vnd.openxmlformats-package.core-properties+xml", "application/vnd.openxmlformats-package.digital-signature-xmlsignature+xml", "application/vnd.openxmlformats-package.relationships+xml", "application/vnd.orange.indata", "application/vnd.osa.netdeploy", "application/vnd.osgeo.mapguide.package", "application/vnd.osgi.bundle", "application/vnd.osgi.dp", "application/vnd.osgi.subsystem", "application/vnd.otps.ct-kip+xml", "application/vnd.palm", "application/vnd.paos.xml", "application/vnd.pawaafile", "application/vnd.pg.format", "application/vnd.pg.osasli", "application/vnd.piaccess.application-licence", "application/vnd.picsel", "application/vnd.pmi.widget", "application/vnd.poc.group-advertisement+xml", "application/vnd.pocketlearn", "application/vnd.powerbuilder6", "application/vnd.powerbuilder6-s", "application/vnd.powerbuilder7", "application/vnd.powerbuilder75", "application/vnd.powerbuilder75-s", "application/vnd.powerbuilder7-s", "application/vnd.preminet", "application/vnd.previewsystems.box", "application/vnd.proteus.magazine", "application/vnd.publishare-delta-tree", "application/vnd.pvi.ptid1", "application/vnd.pwg-multiplexed", "application/vnd.pwg-xhtml-print+xml", "application/vnd.qualcomm.brew-app-res", "application/vnd.Quark.QuarkXPress", "application/vnd.quobject-quoxdocument", "application/vnd.radisys.moml+xml", "application/vnd.radisys.msml-audit-conf+xml", "application/vnd.radisys.msml-audit-conn+xml", "application/vnd.radisys.msml-audit-dialog+xml", "application/vnd.radisys.msml-audit-stream+xml", "application/vnd.radisys.msml-audit+xml", "application/vnd.radisys.msml-conf+xml", "application/vnd.radisys.msml-dialog-base+xml", "application/vnd.radisys.msml-dialog-fax-detect+xml", "application/vnd.radisys.msml-dialog-fax-sendrecv+xml", "application/vnd.radisys.msml-dialog-group+xml", "application/vnd.radisys.msml-dialog-speech+xml", "application/vnd.radisys.msml-dialog-transform+xml", "application/vnd.radisys.msml-dialog+xml", "application/vnd.radisys.msml+xml", "application/vnd.rainstor.data", "application/vnd.rapid", "application/vnd.realvnc.bed", "application/vnd.recordare.musicxml", "application/vnd.recordare.musicxml+xml", "application/vnd.RenLearn.rlprint", "application/vnd.rig.cryptonote", "application/vnd.route66.link66+xml", "application/vnd.rs-274x", "application/vnd.ruckus.download", "application/vnd.s3sms", "application/vnd.sailingtracker.track", "application/vnd.sbm.cid", "application/vnd.sbm.mid2", "application/vnd.scribus", "application/vnd.sealed.3df", "application/vnd.sealed.csf", "application/vnd.sealed.doc", "application/vnd.sealed.eml", "application/vnd.sealed.mht", "application/vnd.sealed.net", "application/vnd.sealed.ppt", "application/vnd.sealed.tiff", "application/vnd.sealed.xls", "application/vnd.sealedmedia.softseal.html", "application/vnd.sealedmedia.softseal.pdf", "application/vnd.seemail", "application/vnd.sema", "application/vnd.semd", "application/vnd.semf", "application/vnd.shana.informed.formdata", "application/vnd.shana.informed.formtemplate", "application/vnd.shana.informed.interchange", "application/vnd.shana.informed.package", "application/vnd.SimTech-MindMapper", "application/vnd.siren+json", "application/vnd.smaf", "application/vnd.smart.notebook", "application/vnd.smart.teacher", "application/vnd.software602.filler.form+xml", "application/vnd.software602.filler.form-xml-zip", "application/vnd.solent.sdkm+xml", "application/vnd.spotfire.dxp", "application/vnd.spotfire.sfs", "application/vnd.sss-cod", "application/vnd.sss-dtf", "application/vnd.sss-ntf", "application/vnd.stepmania.package", "application/vnd.stepmania.stepchart", "application/vnd.street-stream", "application/vnd.sun.wadl+xml", "application/vnd.sus-calendar", "application/vnd.svd", "application/vnd.swiftview-ics", "application/vnd.syncml.dm.notification", "application/vnd.syncml.dmddf+xml", "application/vnd.syncml.dmtnds+wbxml", "application/vnd.syncml.dmtnds+xml", "application/vnd.syncml.dmddf+wbxml", "application/vnd.syncml.dm+wbxml", "application/vnd.syncml.dm+xml", "application/vnd.syncml.ds.notification", "application/vnd.syncml+xml", "application/vnd.tao.intent-module-archive", "application/vnd.tcpdump.pcap", "application/vnd.tmobile-livetv", "application/vnd.trid.tpt", "application/vnd.triscape.mxs", "application/vnd.trueapp", "application/vnd.truedoc", "application/vnd.ubisoft.webplayer", "application/vnd.ufdl", "application/vnd.uiq.theme", "application/vnd.umajin", "application/vnd.unity", "application/vnd.uoml+xml", "application/vnd.uplanet.alert", "application/vnd.uplanet.alert-wbxml", "application/vnd.uplanet.bearer-choice", "application/vnd.uplanet.bearer-choice-wbxml", "application/vnd.uplanet.cacheop", "application/vnd.uplanet.cacheop-wbxml", "application/vnd.uplanet.channel", "application/vnd.uplanet.channel-wbxml", "application/vnd.uplanet.list", "application/vnd.uplanet.listcmd", "application/vnd.uplanet.listcmd-wbxml", "application/vnd.uplanet.list-wbxml", "application/vnd.uplanet.signal", "application/vnd.vcx", "application/vnd.vd-study", "application/vnd.vectorworks", "application/vnd.verimatrix.vcas", "application/vnd.vidsoft.vidconference", "application/vnd.visio", "application/vnd.visionary", "application/vnd.vividence.scriptfile", "application/vnd.vsf", "application/vnd.wap.sic", "application/vnd.wap.slc", "application/vnd.wap.wbxml", "application/vnd.wap.wmlc", "application/vnd.wap.wmlscriptc", "application/vnd.webturbo", "application/vnd.wfa.wsc", "application/vnd.wmc", "application/vnd.wmf.bootstrap", "application/vnd.wolfram.mathematica", "application/vnd.wolfram.mathematica.package", "application/vnd.wolfram.player", "application/vnd.wordperfect", "application/vnd.wqd", "application/vnd.wrq-hp3000-labelled", "application/vnd.wt.stf", "application/vnd.wv.csp+xml", "application/vnd.wv.csp+wbxml", "application/vnd.wv.ssp+xml", "application/vnd.xacml+json", "application/vnd.xara", "application/vnd.xfdl", "application/vnd.xfdl.webform", "application/vnd.xmi+xml", "application/vnd.xmpie.cpkg", "application/vnd.xmpie.dpkg", "application/vnd.xmpie.plan", "application/vnd.xmpie.ppkg", "application/vnd.xmpie.xlim", "application/vnd.yamaha.hv-dic", "application/vnd.yamaha.hv-script", "application/vnd.yamaha.hv-voice", "application/vnd.yamaha.openscoreformat.osfpvg+xml", "application/vnd.yamaha.openscoreformat", "application/vnd.yamaha.remote-setup", "application/vnd.yamaha.smaf-audio", "application/vnd.yamaha.smaf-phrase", "application/vnd.yamaha.through-ngn", "application/vnd.yamaha.tunnel-udpencap", "application/vnd.yellowriver-custom-menu", "application/vnd.zul", "application/vnd.zzazz.deck+xml", "application/voicexml+xml", "application/vq-rtcpxr", "application/watcherinfo+xml", "application/whoispp-query", "application/whoispp-response", "application/widget", "application/wita", "application/wordperfect5.1", "application/wsdl+xml", "application/wspolicy+xml", "application/x400-bp", "application/xcap-att+xml", "application/xcap-caps+xml", "application/xcap-diff+xml", "application/xcap-el+xml", "application/xcap-error+xml", "application/xcap-ns+xml", "application/xcon-conference-info-diff+xml", "application/xcon-conference-info+xml", "application/xenc+xml", "application/xhtml+xml", "application/xml", "application/xml-dtd", "application/xml-external-parsed-entity", "application/xmpp+xml", "application/xop+xml", "application/xslt+xml", "application/xv+xml", "application/yang", "application/yin+xml", "application/zip", "application/zlib", "audio/1d-interleaved-parityfec", "audio/32kadpcm", "audio/3gpp", "audio/3gpp2", "audio/ac3", "audio/AMR", "audio/AMR-WB", "audio/amr-wb+", "audio/asc", "audio/ATRAC-ADVANCED-LOSSLESS", "audio/ATRAC-X", "audio/ATRAC3", "audio/basic", "audio/BV16", "audio/BV32", "audio/clearmode", "audio/CN", "audio/DAT12", "audio/dls", "audio/dsr-es201108", "audio/dsr-es202050", "audio/dsr-es202211", "audio/dsr-es202212", "audio/DV", "audio/DVI4", "audio/eac3", "audio/encaprtp", "audio/EVRC", "audio/EVRC-QCP", "audio/EVRC0", "audio/EVRC1", "audio/EVRCB", "audio/EVRCB0", "audio/EVRCB1", "audio/EVRCNW", "audio/EVRCNW0", "audio/EVRCNW1", "audio/EVRCWB", "audio/EVRCWB0", "audio/EVRCWB1", "audio/example", "audio/fwdred", "audio/G719", "audio/G722", "audio/G7221", "audio/G723", "audio/G726-16", "audio/G726-24", "audio/G726-32", "audio/G726-40", "audio/G728", "audio/G729", "audio/G7291", "audio/G729D", "audio/G729E", "audio/GSM", "audio/GSM-EFR", "audio/GSM-HR-08", "audio/iLBC", "audio/ip-mr_v2.5", "audio/L8", "audio/L16", "audio/L20", "audio/L24", "audio/LPC", "audio/mobile-xmf", "audio/MPA", "audio/mp4", "audio/MP4A-LATM", "audio/mpa-robust", "audio/mpeg", "audio/mpeg4-generic", "audio/ogg", "audio/parityfec", "audio/PCMA", "audio/PCMA-WB", "audio/PCMU", "audio/PCMU-WB", "audio/prs.sid", "audio/QCELP", "audio/raptorfec", "audio/RED", "audio/rtp-enc-aescm128", "audio/rtploopback", "audio/rtp-midi", "audio/rtx", "audio/SMV", "audio/SMV0", "audio/SMV-QCP", "audio/sp-midi", "audio/speex", "audio/t140c", "audio/t38", "audio/telephone-event", "audio/tone", "audio/UEMCLIP", "audio/ulpfec", "audio/VDVI", "audio/VMR-WB", "audio/vnd.3gpp.iufp", "audio/vnd.4SB", "audio/vnd.audiokoz", "audio/vnd.CELP", "audio/vnd.cisco.nse", "audio/vnd.cmles.radio-events", "audio/vnd.cns.anp1", "audio/vnd.cns.inf1", "audio/vnd.dece.audio", "audio/vnd.digital-winds", "audio/vnd.dlna.adts", "audio/vnd.dolby.heaac.1", "audio/vnd.dolby.heaac.2", "audio/vnd.dolby.mlp", "audio/vnd.dolby.mps", "audio/vnd.dolby.pl2", "audio/vnd.dolby.pl2x", "audio/vnd.dolby.pl2z", "audio/vnd.dolby.pulse.1", "audio/vnd.dra", "audio/vnd.dts", "audio/vnd.dts.hd", "audio/vnd.dvb.file", "audio/vnd.everad.plj", "audio/vnd.hns.audio", "audio/vnd.lucent.voice", "audio/vnd.ms-playready.media.pya", "audio/vnd.nokia.mobile-xmf", "audio/vnd.nortel.vbk", "audio/vnd.nuera.ecelp4800", "audio/vnd.nuera.ecelp7470", "audio/vnd.nuera.ecelp9600", "audio/vnd.octel.sbc", "audio/vnd.rhetorex.32kadpcm", "audio/vnd.rip", "audio/vnd.sealedmedia.softseal.mpeg", "audio/vnd.vmx.cvsd", "audio/vorbis", "audio/vorbis-config", "image/cgm", "image/example", "image/fits", "image/g3fax", "image/gif", "image/ief", "image/jp2", "image/jpeg", "image/jpm", "image/jpx", "image/ktx", "image/naplps", "image/png", "image/prs.btif", "image/prs.pti", "image/pwg-raster", "image/svg+xml", "image/t38", "image/tiff", "image/tiff-fx", "image/vnd.adobe.photoshop", "image/vnd.airzip.accelerator.azv", "image/vnd.cns.inf2", "image/vnd.dece.graphic", "image/vnd.djvu", "image/vnd.dwg", "image/vnd.dxf", "image/vnd.dvb.subtitle", "image/vnd.fastbidsheet", "image/vnd.fpx", "image/vnd.fst", "image/vnd.fujixerox.edmics-mmr", "image/vnd.fujixerox.edmics-rlc", "image/vnd.globalgraphics.pgb", "image/vnd.microsoft.icon", "image/vnd.mix", "image/vnd.ms-modi", "image/vnd.net-fpx", "image/vnd.radiance", "image/vnd.sealed.png", "image/vnd.sealedmedia.softseal.gif", "image/vnd.sealedmedia.softseal.jpg", "image/vnd.svf", "image/vnd.wap.wbmp", "image/vnd.xiff", "image/webp", "message/CPIM", "message/delivery-status", "message/disposition-notification", "message/example", "message/external-body", "message/feedback-report", "message/global", "message/global-delivery-status", "message/global-disposition-notification", "message/global-headers", "message/http", "message/imdn+xml", "message/partial", "message/rfc822", "message/s-http", "message/sip", "message/sipfrag", "message/tracking-status", "model/example", "model/iges", "model/mesh", "model/vnd.collada+xml", "model/vnd.dwf", "model/vnd.flatland.3dml", "model/vnd.gdl", "model/vnd.gs-gdl", "model/vnd.gtw", "model/vnd.moml+xml", "model/vnd.mts", "model/vnd.parasolid.transmit.binary", "model/vnd.parasolid.transmit.text", "model/vnd.vtu", "model/vrml", "multipart/alternative", "multipart/appledouble", "multipart/byteranges", "multipart/digest", "multipart/encrypted", "multipart/example", "multipart/form-data", "multipart/header-set", "multipart/mixed", "multipart/parallel", "multipart/related", "multipart/report", "multipart/signed", "multipart/voice-message", "text/1d-interleaved-parityfec", "text/calendar", "text/css", "text/csv", "text/dns", "text/encaprtp", "text/enriched", "text/example", "text/fwdred", "text/grammar-ref-list", "text/html", "text/jcr-cnd", "text/mizar", "text/n3", "text/parityfec", "text/plain", "text/provenance-notation", "text/prs.fallenstein.rst", "text/prs.lines.tag", "text/raptorfec", "text/RED", "text/rfc822-headers", "text/richtext", "text/rtf", "text/rtp-enc-aescm128", "text/rtploopback", "text/rtx", "text/sgml", "text/t140", "text/tab-separated-values", "text/troff", "text/turtle", "text/ulpfec", "text/uri-list", "text/vcard", "text/vnd.abc", "text/vnd.curl", "text/vnd.debian.copyright", "text/vnd.DMClientScript", "text/vnd.dvb.subtitle", "text/vnd.esmertec.theme-descriptor", "text/vnd.fly", "text/vnd.fmi.flexstor", "text/vnd.graphviz", "text/vnd.in3d.3dml", "text/vnd.in3d.spot", "text/vnd.IPTC.NewsML", "text/vnd.IPTC.NITF", "text/vnd.latex-z", "text/vnd.motorola.reflex", "text/vnd.ms-mediapackage", "text/vnd.net2phone.commcenter.command", "text/vnd.radisys.msml-basic-layout", "text/vnd.sun.j2me.app-descriptor", "text/vnd.trolltech.linguist", "text/vnd.wap.si", "text/vnd.wap.sl", "text/vnd.wap.wml", "text/vnd.wap.wmlscript", "text/xml", "text/xml-external-parsed-entity", "video/1d-interleaved-parityfec", "video/3gpp", "video/3gpp2", "video/3gpp-tt", "video/BMPEG", "video/BT656", "video/CelB", "video/DV", "video/encaprtp", "video/example", "video/H261", "video/H263", "video/H263-1998", "video/H263-2000", "video/H264", "video/H264-RCDO", "video/H264-SVC", "video/JPEG", "video/jpeg2000", "video/MJ2", "video/MP1S", "video/MP2P", "video/MP2T", "video/mp4", "video/MP4V-ES", "video/MPV", "video/mpeg", "video/mpeg4-generic", "video/nv", "video/ogg", "video/parityfec", "video/pointer", "video/quicktime", "video/raptorfec", "video/raw", "video/rtp-enc-aescm128", "video/rtploopback", "video/rtx", "video/SMPTE292M", "video/ulpfec", "video/vc1", "video/vnd.CCTV", "video/vnd.dece.hd", "video/vnd.dece.mobile", "video/vnd.dece.mp4", "video/vnd.dece.pd", "video/vnd.dece.sd", "video/vnd.dece.video", "video/vnd.directv.mpeg", "video/vnd.directv.mpeg-tts", "video/vnd.dlna.mpeg-tts", "video/vnd.dvb.file", "video/vnd.fvt", "video/vnd.hns.video", "video/vnd.iptvforum.1dparityfec-1010", "video/vnd.iptvforum.1dparityfec-2005", "video/vnd.iptvforum.2dparityfec-1010", "video/vnd.iptvforum.2dparityfec-2005", "video/vnd.iptvforum.ttsavc", "video/vnd.iptvforum.ttsmpeg2", "video/vnd.motorola.video", "video/vnd.motorola.videop", "video/vnd.mpegurl", "video/vnd.ms-playready.media.pyv", "video/vnd.nokia.interleaved-multimedia", "video/vnd.nokia.videovoip", "video/vnd.objectvideo", "video/vnd.radgamettools.bink", "video/vnd.radgamettools.smacker", "video/vnd.sealed.mpeg1", "video/vnd.sealed.mpeg4", "video/vnd.sealed.swf", "video/vnd.sealedmedia.softseal.mov", "video/vnd.uvvu.mp4", "video/vnd.vivo", "video/webm" };
    public static String[] httpCharSets = { "Adobe-Standard-Encoding", "Adobe-Symbol-Encoding", "Amiga-1251", "ANSI_X3.110-1983", "ASMO_449", "Big5", "Big5-HKSCS", "BOCU-1", "BRF", "BS_4730", "BS_viewdata", "CESU-8", "CP50220", "CP51932", "CSA_Z243.4-1985-1", "CSA_Z243.4-1985-2", "CSA_Z243.4-1985-gr", "CSN_369103", "DEC-MCS", "DIN_66003", "dk-us", "DS_2089", "EBCDIC-AT-DE", "EBCDIC-AT-DE-A", "EBCDIC-CA-FR", "EBCDIC-DK-NO", "EBCDIC-DK-NO-A", "EBCDIC-ES", "EBCDIC-ES-A", "EBCDIC-ES-S", "EBCDIC-FI-SE", "EBCDIC-FI-SE-A", "EBCDIC-FR", "EBCDIC-IT", "EBCDIC-PT", "EBCDIC-UK", "EBCDIC-US", "ECMA-cyrillic", "ES", "ES2", "EUC-JP", "EUC-KR", "GB18030", "GB2312", "GB_1988-80", "GB_2312-80", "GBK", "GOST_19768-74", "greek-ccitt", "greek7", "greek7-old", "HP-DeskTop", "HP-Legal", "HP-Math8", "HP-Pi-font", "hp-roman8", "HZ-GB-2312", "IBM-Symbols", "IBM-Thai", "IBM00858", "IBM00924", "IBM01140", "IBM01141", "IBM01142", "IBM01143", "IBM01144", "IBM01145", "IBM01146", "IBM01147", "IBM01148", "IBM01149", "IBM037", "IBM038", "IBM1026", "IBM1047", "IBM273", "IBM274", "IBM275", "IBM277", "IBM278", "IBM280", "IBM281", "IBM284", "IBM285", "IBM290", "IBM297", "IBM420", "IBM423", "IBM424", "IBM437", "IBM500", "IBM775", "IBM850", "IBM851", "IBM852", "IBM855", "IBM857", "IBM860", "IBM861", "IBM862", "IBM863", "IBM864", "IBM865", "IBM866", "IBM868", "IBM869", "IBM870", "IBM871", "IBM880", "IBM891", "IBM903", "IBM904", "IBM905", "IBM918", "IEC_P27-1", "INIS", "INIS-8", "INIS-cyrillic", "INVARIANT", "ISO-10646-J-1", "ISO-10646-UCS-2", "ISO-10646-UCS-4", "ISO-10646-UCS-Basic", "ISO-10646-Unicode-Latin1", "ISO-10646-UTF-1", "ISO-11548-1", "ISO-2022-CN", "ISO-2022-CN-EXT", "ISO-2022-JP", "ISO-2022-JP-2", "ISO-2022-KR", "ISO-8859-1", "ISO-8859-1-Windows-3.0-Latin-1", "ISO-8859-1-Windows-3.1-Latin-1", "ISO-8859-10", "ISO-8859-13", "ISO-8859-14", "ISO-8859-15", "ISO-8859-16", "ISO-8859-2", "ISO-8859-2-Windows-Latin-2", "ISO-8859-3", "ISO-8859-4", "ISO-8859-5", "ISO-8859-6", "ISO-8859-6-E", "ISO-8859-6-I", "ISO-8859-7", "ISO-8859-8", "ISO-8859-8-E", "ISO-8859-8-I", "ISO-8859-9", "ISO-8859-9-Windows-Latin-5", "ISO-8859-supp", "iso-ir-90", "ISO-Unicode-IBM-1261", "ISO-Unicode-IBM-1264", "ISO-Unicode-IBM-1265", "ISO-Unicode-IBM-1268", "ISO-Unicode-IBM-1276", "ISO_10367-box", "ISO_2033-1983", "ISO_5427", "ISO_5427:1981", "ISO_5428:1980", "ISO_646.basic:1983", "ISO_646.irv:1983", "ISO_6937-2-25", "ISO_6937-2-add", "IT", "JIS_C6220-1969-jp", "JIS_C6220-1969-ro", "JIS_C6226-1978", "JIS_C6226-1983", "JIS_C6229-1984-a", "JIS_C6229-1984-b", "JIS_C6229-1984-b-add", "JIS_C6229-1984-hand", "JIS_C6229-1984-hand-add", "JIS_C6229-1984-kana", "JIS_Encoding", "JIS_X0201", "JIS_X0212-1990", "JUS_I.B1.002", "JUS_I.B1.003-mac", "JUS_I.B1.003-serb", "KOI7-switched", "KOI8-R", "KOI8-U", "KS_C_5601-1987", "KSC5636", "KZ-1048", "latin-greek", "Latin-greek-1", "latin-lap", "macintosh", "Microsoft-Publishing", "MNEM", "MNEMONIC", "MSZ_7795.3", "NATS-DANO", "NATS-DANO-ADD", "NATS-SEFI", "NATS-SEFI-ADD", "NC_NC00-10:81", "NF_Z_62-010", "NF_Z_62-010_(1973)", "NS_4551-1", "NS_4551-2", "OSD_EBCDIC_DF03_IRV", "OSD_EBCDIC_DF04_1", "OSD_EBCDIC_DF04_15", "PC8-Danish-Norwegian", "PC8-Turkish", "PT", "PT2", "PTCP154", "SCSU", "SEN_850200_B", "SEN_850200_C", "Shift_JIS", "T.101-G2", "T.61-7bit", "T.61-8bit", "TIS-620", "TSCII", "UNICODE-1-1", "UNICODE-1-1-UTF-7", "UNKNOWN-8BIT", "US-ASCII", "us-dk", "UTF-16", "UTF-16BE", "UTF-16LE", "UTF-32", "UTF-32BE", "UTF-32LE", "UTF-7", "UTF-8", "Ventura-International", "Ventura-Math", "Ventura-US", "videotex-suppl", "VIQR", "VISCII", "windows-1250", "windows-1251", "windows-1252", "windows-1253", "windows-1254", "windows-1255", "windows-1256", "windows-1257", "windows-1258", "Windows-31J", "windows-874" };
    public static String[] httpCORS = { "same-site", "same-origin" };
    public static String[] httpExpectCT = { "report-uri=", "enforce", "max-age=" };
    public static String[] httpFeaturePolicyDirectives = { "autoplay", "camera", "document-domain", "encrypted-media", "fullscreen", "geolocation", "microphone", "midi", "payment", "vr", "xr" };
    public static String[] httpKeepAliveDirectives = { "timeout=", "max=" };
    public static String[] httpLocationBaseUrls = { "/", "http://", "https://" };
    public static String[] httpPragmaDirectives = { "no-cache" };
    public static String[] httpAuthenticationTypes = { "Basic", "Digest", "NTLM" };
    public static String[] httpPublicKeyPinsDirectives = { "pin-sha256=", "max-age=", "includeSubDomains", "report-uri=" };
    public static String[] httpReferrerPolicyDirectives = { "no-referrer-when-downgrade", "no-referrer", "origin", "origin-when-cross-origin", "same-origin", "strict-origin", "strict-origin-when-cross-origin", "unsafe-url" };
    public static String[] httpSetCookieDirectives = { "Expires=", "Max-Age=", "Domain=", "Path=", "Secure", "HttpOnly", "SameSite=Strict", "SameSite=Lax" };
    public static String[] httpStrictTransportSecurityDirectives = { "max-age=", "includeSubDomains", "preload" };
    public static String[] httpStatusCodes = { "100 Continue", "101 Switching Protocols", "102 Processing", "200 OK", "201 Created", "202 Accepted", "204 No Content", "205 Reset Content", "206 Partial Content", "207 Multi-Status", "208 Already Reported", "226 IM Used", "300 Multiple Choices", "301 Moved Permanently", "302 Found", "304 Not Modified", "306 Switch Proxy", "400 Bad Request", "401 Unauthorized", "402 Payment Required", "403 Forbidden", "404 Not Found", "405 Method Not Allowed", "406 Not Acceptable", "407 Proxy Authentication Required", "408 Request Timeout", "409 Conflict", "410 Gone", "411 Length Required", "412 Precondition Failed", "413 Request Entity Too Large", "414 Request-URI Too Long", "415 Unsupported Media Type", "416 Requested Range Not Satisfiable", "417 Expectation Failed", "419 Authentication Timeout", "422 Unprocessable Entity", "423 Locked", "424 Failed Dependency", "424 Method Failure", "425 Unordered Collection", "426 Upgrade Required", "428 Precondition Required", "429 Too Many Requests", "431 Request Header Fields Too Large", "444 No Response", "449 Retry With", "450 Blocked by Windows Parental Controls", "451 Unavailable For Legal Reasons", "451 Redirect", "494 Request Header Too Large", "495 Cert Error", "496 No Cert", "497 HTTP to HTTPS", "499 Client Closed Request", "500 Internal Server Error", "501 Not Implemented", "502 Bad Gateway", "503 Service Unavailable", "503 Service Temporarily Unavailable", "504 Gateway Timeout", "505 HTTP Version Not Supported", "506 Variant Also Negotiates", "507 Insufficient Storage", "508 Loop Detected", "509 Bandwidth Limit Exceeded", "510 Not Extended", "511 Network Authentication Required", "598 Network read timeout error", "599 Network connect timeout error" };
    public static String[] httpStatusCodes11 = { "203 Non-Authoritative Information", "303 See Other", "305 Use Proxy", "307 Temporary Redirect", "308 Permanent Redirect" };
    public static String[] httpTransferEncodingValues = { "chunked", "compress", "deflate", "gzip", "identity" };
    public static String[] httpViaProtocols = { "1.0", "1.1", "HTTP/1.0", "HTTP/1.1" };
    public static String[] httpWarningCodes = { "110", "111", "112", "113", "199", "214", "299" };
    public static String[] httpWarningTexts = { "Response is Stale", "Revalidation Failed", "Disconnected Operation", "Heuristic Expiration", "Miscellaneous Warning", "Transformation Applied", "Misc. Persistent Warning" };
    public static String[] httpXDnsPrefetchControlDirectives = { "on", "off" };
    public static String[] httpXFrameOptions = { "deny", "sameorigin", "allow-from " };
    public static String[] httpXContentTypeOptions = { "nosniff" };
    public static String[] httpCSP = { "default-src", "script-src", "object-src", "style-src", "img-src", "media-src", "frame-src", "font-src", "manifest-src", "prefetch-src", "webrtc-src", "worker-src", "connect-src" };
    public static String[] httpXXssProtectionValues = { "0", "1", "1; mode=block", "1; report=" };
    public static String[] httpXUACompatibleValues = { "IE=edge", "IE=7", "IE=8", "IE=9", "IE=10", "IE=11", "IE=EmulateIE7", "IE=EmulateIE8", "IE=EmulateIE9", "IE=EmulateIE10", "IE=EmulateIE11", "Chrome=1" };
    public static String[] httpXRobotsTagDirectives = { "all", "noindex", "nofollow", "none", "noarchive", "nosnippet", "noodp", "notranslate", "noimageindex", "unavailable_after:" };

    public static String[] dbConnectFuncs = { "mysql_connect", "mysqli_connect", "mysqli::__construct", "PDO::__construct" };
    public static String[] dbConnectElements = { "localhost" };
    public static String[] dbSelectDbFuncs = { "mysql_select_db:0", "mysql_selectdb:0", "mysqli_connect:3", "mysqli_select_db:1", "mysqli_change_user:3", "mysqli::__construct:3", "mysqli::select_db:0", "mysqli::change_user:2" };
    public static String[] dbConnectUserFuncs = { "mysql_connect:1", "mysqli_connect:1", "mysqli::__construct:1", "mysqli_change_user:1", "mysqli::change_user:0", "PDO::__construct:1" };
    public static String[] dbCharSetFuncs = { "mysql_set_charset:0", "mysqli_set_charset:1", "mysqli::set_charset:0" };
    public static String[] dbCharSets = { "armscii8", "ascii", "big5", "binary", "cp1250", "cp1251", "cp1256", "cp1257", "cp850", "cp852", "cp866", "cp932", "dec8", "eucjpms", "euckr", "gb2312", "gbk", "geostd8", "greek", "hebrew", "hp8", "iso-8859-1", "iso-8859-13", "iso-8859-2", "iso-8859-7", "iso-8859-8", "iso-8859-9", "keybcs2", "koi8r", "koi8u", "latin1", "latin2", "latin5", "latin7", "macce", "macroman", "sjis", "swe7", "tis620", "ucs2", "ujis", "utf8" };
    public static String[] dbCharSetsInfos = { "ARMSCII-8 Armenian", "US ASCII", "Big5 Traditional Chinese", "Binary pseudo charset", "Windows Central European", "Windows Cyrillic", "Windows Arabic", "Windows Baltic", "DOS West European", "DOS Central European", "DOS Russian", "SJIS for Windows Japanese", "DEC West European", "UJIS for Windows Japanese", "EUC-KR Korean", "GB2312 Simplified Chinese", "GBK Simplified Chinese", "GEOSTD8 Georgian", "ISO 8859-7 Greek", "ISO 8859-8 Hebrew", "HP West European", "cp1252 West European", "ISO 8859-13 Baltic", "ISO 8859-2 Central European", "ISO 8859-7 Greek", "ISO 8859-8 Hebrew", "ISO 8859-9 Turkish", "DOS Kamenicky Czech-Slovak", "KOI8-R Relcom Russian", "KOI8-U Ukrainian", "cp1252 West European", "ISO 8859-2 Central European", "ISO 8859-9 Turkish", "ISO 8859-13 Baltic", "Mac Central European", "Mac West European", "Shift-JIS Japanese", "7bit Swedish", "TIS620 Thai", "UCS-2 Unicode", "EUC-JP Japanese", "UTF-8 Unicode" };

    public static String[] phpExtensionElements = { "amqp", "apache", "apc", "apd", "bbcode", "bcmath", "bcompiler", "bz2", "cairo", "calendar", "chdb", "classkit", "com", "crack", "ctype", "cubrid", "curl", "cyrus", "dba", "dbase", "dbplus", "dbx", "dio", "dom", "dotnet", "eio", "enchant", "ev", "event", "exif", "expect", "fam", "fbsql", "fdf", "fileinfo", "filepro", "filter", "fribidi", "ftp", "gearman", "gender", "geoip", "gettext", "gmagick", "gmp", "gnupg", "gupnp", "haru", "htscanner", "pecl_http", "hyperwave", "hwapi", "interbase", "ibm_db2", "iconv", "id3", "informix", "iisfunc", "gd", "imagick", "imap", "inclued", "ingres", "inotify", "intl", "java", "json", "judy", "kadm5", "ktaglib", "lapack", "ldap", "libevent", "libxml", "lua", "lzf", "mailparse", "maxdb", "mbstring", "mcrypt", "mcve", "memcache", "memcached", "memtrack", "mhash", "ming", "mnogosearch", "mongo", "mqseries", "msession", "msql", "mssql", "mysql", "mysqli", "mysqlnd", "mysqlnd_memcache", "mysqlnd_ms", "mysqlnd_mux", "mysqlnd_qc", "mysqlnd_uh", "ncurses", "net_gopher", "newt", "notes", "nsapi", "oauth", "oci8", "oggvorbis", "openal", "openssl", "ovrimos", "paradox", "parsekit", "pcntl", "pcre", "pdflib", "pdo", "pdo_4d", "pdo_cubrid", "pdo_dblib", "pdo_firebird", "pdo_ibm", "pdo_informix", "pdo_mysql", "pdo_oci", "pdo_odbc", "pdo_pgsql", "pdo_sqlite", "pdo_sqlsrv", "pdo_pgsql", "phar", "posix", "printer", "proctitle", "ps", "pspell", "pthreads", "qtdom", "quickhash", "radius", "rar", "readline", "recode", "rpmreader", "rrd", "runkit", "sam", "sca", "scream", "sca_sdo", "sysvmsg", "session", "session_pgsql", "shmop", "simplexml", "snmp", "soap", "sockets", "solr", "sphinx", "spl_types", "spplus", "sqlite", "sqlite3", "sqlsrv", "ssdeep", "ssh2", "stats", "stomp", "svm", "svn", "swf", "swish", "sybase", "taint", "tcpwrap", "tidy", "tokenizer", "tokyo_tyrant", "trader", "odbc", "v8js", "varnish", "vpopmail", "w32api", "wddx", "weakref", "win32ps", "win32service", "wincache", "xattr", "xdiff", "xhprof", "xml", "xmlreader", "xmlrpc", "xmlwriter", "xsl", "xslt", "yaf", "yaml", "yaz", "zip", "zlib" };

    public static String[] htmlCharSets = { "ISO-8859-1", "ISO-8859-5", "ISO-8859-15", "UTF-8", "cp866", "cp1251", "cp1252", "KOI8-R", "BIG5", "GB2312", "BIG5-HKSCS", "Shift_JIS", "EUC-JP", "MacRoman" };

    public static String[] mbStringEncodingElements = { "pass", "auto", "wchar", "byte2be", "byte2le", "byte4be", "byte4le", "BASE64", "UUENCODE", "HTML-ENTITIES", "Quoted-Printable", "7bit", "8bit", "UCS-4", "UCS-4BE", "UCS-4LE", "UCS-2", "UCS-2BE", "UCS-2LE", "UTF-32", "UTF-32BE", "UTF-32LE", "UTF-16", "UTF-16BE", "UTF-16LE", "UTF-8", "UTF-7", "UTF7-IMAP", "ASCII", "EUC-JP", "SJIS", "eucJP-win", "SJIS-win", "JIS", "ISO-2022-JP", "Windows-1252", "ISO-8859-1", "ISO-8859-2", "ISO-8859-3", "ISO-8859-4", "ISO-8859-5", "ISO-8859-6", "ISO-8859-7", "ISO-8859-8", "ISO-8859-9", "ISO-8859-10", "ISO-8859-13", "ISO-8859-14", "ISO-8859-15", "EUC-CN", "CP936", "HZ", "EUC-TW", "BIG-5", "EUC-KR", "UHC", "ISO-2022-KR", "Windows-1251", "CP866", "KOI8-R" };
    public static String[] mbStringInfoTypes = { "all", "http_output", "http_input", "internal_encoding", "func_overload" };
    public static String[] mbStringLanguageElements = { "uni", "en", "English", "ja", "Japanese" };

    public static String[] obHandlerElements = { "ob_gzhandler", "ob_iconv_handler", "ob_tidyhandler", "ob_deflatehandler", "ob_inflatehandler", "ob_etaghandler", "mb_output_handler" };

    public static String[] dateFormatTokens = { "d", "D", "j", "jS", "l", "N", "S", "w", "z", "W", "F", "m", "M", "n", "t", "L", "o", "Y", "y", "a", "A", "B", "g", "G", "h", "H", "i", "s", "u", "e", "I", "O", "P", "T", "Z", "c", "r", "U", "H:i", "H:i:s", "m.d.y", "m.d.Y", "m/d/Y", "d.m.y", "d.m.Y", "d.m.Y H:i:s", "Ymd", "Y-m-d", "Y-m-d H:i:s", "Y/m/d H:i:s", "F j, Y, g:i a" };
    public static String[] dateFormatInfos = { "Day of month (01..31)", "Weekday (Mon..Sun)", "Day of month (1..31)", "Day of month (1st..31th)", "Weekday (Monday..Sunday)", "Weekday (1..7)", "Day of month suffix (st, nd, rd, th)", "Weekday (0..6)", "Day of year (0..365)", "Week of year (01..53)", "Month (January..December)", "Month (01..12)", "Month (Jan..Dec)", "Month (1..12)", "Number of days in month (28..31)", "Leap year (1=yes, 0=no)", "Year for ISO-8601 week (ex. 2014)", "Year (ex. 2014)", "Year (ex. 14)", "am or pm", "AM or PM", "Swatch Internet time (000..999)", "Hour (1..12)", "Hour (0..23)", "Hour (01..12)", "Hour (00..23)", "Minutes (00..59)", "Seconds (00..59)", "Microseconds (000000..999999)", "Timezone identifier (UTC, GMT, ...)", "Daylight Saving Time (1=yes, 0=no)", "GMT in hours (ex. +0200)", "GMT with colon (ex. +02:00)", "Timezone abbreviation (EST, MDT, ...)", "Timezone offset in seconds (-43200..50400)", "ISO 8601 formatted date", "RFC 2822 formatted date", "Seconds since Jan 1st, 1970", "Hour and minutes", "Hour, minutes and seconds", "Month, day, year", "Month, day, year", "Month, day, year", "Day, month, year", "Day, month, year", "Day, month, year, hour, minutes, seconds", "Year, month, day", "Year, month, day", "Year, month, day, hour, minutes, seconds", "Year, month, day, hour, minutes, seconds", "Month, day, year, hour, minutes, am/pm" };

    public static String[] timeFormatTokens = { "%a", "%A", "%d", "%e", "%j", "%u", "%w", "%U", "%V", "%W", "%h", "%b", "%B", "%m", "%C", "%g", "%G", "%y", "%Y", "%H", "%k", "%I", "%l", "%M", "%p", "%P", "%r", "%R", "%S", "%T", "%X", "%z", "%Z", "%c", "%D", "%F", "%s", "%x", "%n", "%t", "%%", "%d.%m.%y", "%d.%m.%Y", "%d.%m.%Y %H:%M:%S", "%H:%M", "%H:%M:%S", "%m.%d.%y", "%m.%d.%Y", "%m/%d/%Y", "%Y%m%d", "%Y-%m-%d", "%Y-%m-%d %H:%M:%S", "%Y/%m/%d %H:%M:%S" };
    public static String[] timeFormatInfos = { "Weekday (Mon..Sun)", "Weekday (Monday..Sunday)", "Day of month (01..31)", "Day of month (1..31)", "Day of year (001..366)", "Weekday (1..7 =Mon..Sun)", "Weekday (0..6 =Sun..Sat)", "Full week number (1..52)", "Week of year (01..53)", "Week of year (1..53)", "Month (Jan..Dec)", "Month (Jan..Dec)", "Month (January..December)", "Month (01..12)", "Century (ex. 20)", "Year in ISO-8601 (ex. 14)", "Year in ISO-8601 (ex. 2014)", "Year (ex. 14)", "Year (ex. 2014)", "Hour (00..23)", "Hour (0..23)", "Hour (01..12)", "Hour (1..12)", "Minutes (00..59)", "AM or PM", "am or pm", "hour, minutes, seconds, am/pm", "hour and minutes", "Seconds (00..59)", "Hour, minutes and seconds", "Locale based time", "Timezone offset (ex. -0500)", "Timezone abbreviation (EST, MDT, ...)", "Locale based date and time", "Month, day, year", "Year, month, day", "Unix timestamp", "Locale based date", "Newline character", "Tab character", "Percentage character", "Day, month, year", "Day, month, year", "Day, month, year, hour, minutes, seconds", "Hour and minutes", "Hour, minutes and seconds", "Month, day, year", "Month, day, year", "Month, day, year", "Year, month, day", "Year, month, day", "Year, month, day, hour, minutes, seconds", "Year, month, day, hour, minutes, seconds" };

    public static String[] dateTimeRelativeFormats = { "yesterday", "midnight", "today", "now", "noon", "tomorrow", "back of ", "front of ", "first ", "last ", "first day of ", "last day of ", "second ", "third ", "fourth ", "fifth ", "sixth ", "seventh ", "eighth ", "ninth ", "tenth ", "eleventh ", "twelfth ", "next ", "last ", "previous ", "this " };
    public static String[] dateTimeAgo = { "ago" };
    public static String[] dateTimeDayNames = { "sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sun", "mon", "tue", "wed", "thu", "fri", "sat" };
    public static String[] dateTimeDayTexts = { "weekday", "weekdays" };
    public static String[] dateTimeDayRelTexts = { "yesterday", "today", "tomorrow" };
    public static String[] dateTimeDaytimeTexts = { "midnight", "noon" };
    public static String[] dateTimeHourTexts = { "0", "1am", "2am", "3am", "4am", "5am", "6am", "7am", "8am", "9am", "10am", "11am", "12am", "1pm", "2pm", "3pm", "4pm", "5pm", "6pm", "7pm", "8pm", "9pm", "10pm", "11pm", "12pm", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" };
    public static String[] dateTimeMonthNames = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
    public static String[] dateTimeMonthRelTexts = { "previous month", "next month", "this month" };
    public static String[] dateTimeOfPrefixes = { "back ", "front ", "first day ", "last day ", "first sunday ", "first monday ", "first tuesday ", "first wednesday ", "first thursday ", "first friday ", "first saturday ", "last sunday ", "last monday ", "last tuesday ", "last wednesday ", "last thursday ", "last friday ", "last saturday " };
    public static String[] dateTimeDayOfTexts = { "first sun of ", "first sunday of ", "first mon of ", "first monday of ", "first tue of ", "first tuesday of ", "first wed of ", "first wednesday of ", "first thu of ", "first thursday of ", "first fri of ", "first friday of ", "first sat of ", "first saturday of ", "last sun of ", "last sunday of ", "last mon of ", "last monday of ", "last tue of ", "last tuesday of ", "last wed of ", "last wednesday of ", "last thu of ", "last thursday of ", "last fri of ", "last friday of ", "last sat of ", "last saturday of " };
    public static String[] dateTimeOrdinal = { "first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth", "eleventh", "twelfth", "next", "last", "previous", "this" };
    public static String[] dateTimeRelTexts = { "next", "last", "previous", "this" };
    public static String[] dateTimeRelWeek = { "next week", "last week", "previous week", "this week" };
    public static String[] dateTimeUnits = { "sec", "second", "min", "minute", "hour", "day", "fortnight", "month", "year", "week", "weekday" };
    public static String[] dateTimeUnits2 = { "secs", "seconds", "mins", "minutes", "hours", "days", "fortnights", "months", "years", "weeks", "weekdays" };

    public static String[] fileModeElements = { "r", "r+", "w", "w+", "a", "a+", "x", "x+", "c", "c+" };
    public static String[] fileModeInfos = {
            "Read from beginning", "Read/write from beginning",
            "Create/overwrite from beginning", "Create/overwrite/read from beginning",
            "Create/append at end", "Create/append/read at end",
            "Create/write from beginning (fails if file exists)", "Create/write/read from beginning (fails if file exists)",
            "Create/write from beginning", "Create/write/read from beginning"
    };

    public static String[] socketTransports = { "", "tcp://", "udp://", "ssl://", "sslv2://", "sslv3://", "tls://", "unix://", "udg://" };

    public static String[] envNames = {
            "PHP_SELF",
            "GATEWAY_INTERFACE",
            "SERVER_ADDR",
            "SERVER_NAME",
            "SERVER_SOFTWARE",
            "SERVER_PROTOCOL",
            "REQUEST_METHOD",
            "REQUEST_TIME",
            "QUERY_STRING",
            "DOCUMENT_ROOT",
            "HTTP_ACCEPT",
            "HTTP_ACCEPT_CHARSET",
            "HTTP_ACCEPT_ENCODING",
            "HTTP_ACCEPT_LANGUAGE",
            "HTTP_CONNECTION",
            "HTTP_HOST",
            "HTTP_REFERER",
            "HTTP_USER_AGENT",
            "HTTPS",
            "REMOTE_ADDR",
            "REMOTE_HOST",
            "REMOTE_PORT",
            "SCRIPT_FILENAME",
            "SERVER_ADMIN",
            "SERVER_PORT",
            "SERVER_SIGNATURE",
            "PATH_TRANSLATED",
            "SCRIPT_NAME",
            "REQUEST_URI",
            "PHP_AUTH_DIGEST",
            "PHP_AUTH_USER",
            "PHP_AUTH_PW",
            "AUTH_TYPE",
            "PATH_INFO",
            "ORIG_PATH_INFO",
    };

    public static String[] packCodes = {
        "a",
        "A",
        "h",
        "H",
        "c",
        "C",
        "s",
        "S",
        "n",
        "v",
        "i",
        "I",
        "l",
        "L",
        "N",
        "V",
        "q",
        "Q",
        "J",
        "P",
        "f",
        "g",
        "G",
        "d",
        "e",
        "E",
        "x",
        "X",
        "Z",
        "@"
    };
    public static String[] packCodesInfos = {
        "NUL-padded string",
        "SPACE-padded string",
        "Hex string, low nibble first",
        "Hex string, high nibble first",
        "signed char",
        "unsigned char",
        "signed short (always 16 bit, machine byte order)",
        "unsigned short (always 16 bit, machine byte order)",
        "unsigned short (always 16 bit, big endian byte order)",
        "unsigned short (always 16 bit, little endian byte order)",
        "signed integer (machine dependent size and byte order)",
        "unsigned integer (machine dependent size and byte order)",
        "signed long (always 32 bit, machine byte order)",
        "unsigned long (always 32 bit, machine byte order)",
        "unsigned long (always 32 bit, big endian byte order)",
        "unsigned long (always 32 bit, little endian byte order)",
        "signed long long (always 64 bit, machine byte order)",
        "unsigned long long (always 64 bit, machine byte order)",
        "unsigned long long (always 64 bit, big endian byte order)",
        "unsigned long long (always 64 bit, little endian byte order)",
        "float (machine dependent size and representation)",
        "float (machine dependent size, little endian byte order)",
        "float (machine dependent size, big endian byte order)",
        "double (machine dependent size and representation)",
        "double (machine dependent size, little endian byte order)",
        "double (machine dependent size, big endian byte order)",
        "NUL byte",
        "Back up one byte",
        "NUL-padded string",
        "NUL-fill to absolute position"
    };

    public static String[] formatFuncs = { "printf:0", "sprintf:0", "fprintf:1", "vprintf:0", "vsprintf:0", "vfprintf:1" };
    public static String[] formatTokens = { "%", "b", "c", "d", "e", "E", "f", "F", "g", "G", "o", "s", "u", "x", "X" };
    public static String[] formatInfos = { "", "integer", "integer", "integer", "double", "double", "double", "double", "double", "double", "integer", "string", "integer", "integer", "integer" };
    public static String[] formatFlags = { "-", "+", "0", "'" };

    public static String[] formatFuncFqns = { "\\printf", "\\sprintf", "\\fprintf", "\\vprintf", "\\vsprintf", "\\vfprintf", "\\sscanf", "\\fscanf" };

    public final static HashMap<String, String> formatTokensDoc = new HashMap<String, String>() {{
        put("-", "Left-justify within the given field width; Right justification is the default.");
        put("+", "Prefix positive numbers with a plus sign +; Default only negative are prefixed with a negative sign.");
        put(" ", "Pads the result with spaces. This is the default.");
        put("0", "Only left-pads numbers with zeros. With s specifiers this can also right-pad with zeros.");
        put("'", "Pads the result with the following character.");
        put("%", "A literal percent character. No argument is required.");
        put("b", "The argument is treated as an integer and presented as a binary number.");
        put("c", "The argument is treated as an integer and presented as the character with that ASCII.");
        put("d", "The argument is treated as an integer and presented as a (signed) decimal number.");
        put("e", "The argument is treated as scientific notation (e.g. 1.2e+2). The precision specifier stands for the number of digits after the decimal point since PHP 5.2.1. In earlier versions, it was taken as number of significant digits (one less).");
        put("E", "Like the e specifier but uses uppercase letter (e.g. 1.2E+2).");
        put("f", "The argument is treated as a float and presented as a floating-point number (locale aware).");
        put("F", "The argument is treated as a float and presented as a floating-point number (non-locale aware). Available as of PHP 5.0.3.");
        put("g", "General format.<br><br>Let P equal the precision if nonzero, 6 if the precision is omitted, or 1 if the precision is zero. Then, if a conversion with style E would have an exponent of X:<br><br>If P &gt; X ≥ −4, the conversion is with style f and precision P − (X + 1). Otherwise, the conversion is with style e and precision P − 1.");
        put("G", "Like the g specifier but uses E and F.");
        put("o", "The argument is treated as an integer and presented as an octal number.");
        put("s", "The argument is treated and presented as a string.");
        put("u", "The argument is treated as an integer and presented as an unsigned decimal number.");
        put("x", "The argument is treated as an integer and presented as a hexadecimal number (with lowercase letters).");
        put("X", "The argument is treated as an integer and presented as a hexadecimal number (with uppercase letters).");
    }};

    public static String[] scanFormatFuncs = { "sscanf:1", "fscanf:1" };
    public static String[] scanFormatTokens = { "%", "c",     "d",       "D",        "e",     "E",      "f",     "i",        "n", "o", "s", "u", "x", "X" };
    public static String[] scanFormatInfos = { "", "integer", "integer", "integer", "double", "double", "double", "integer", "integer", "integer", "string", "integer", "integer", "integer" };

    public final static HashMap<String, String> scanFormatTokensDoc = new HashMap<String, String>() {{
        put("-", "Left-justify within the given field width; Right justification is the default.");
        put("+", "Prefix positive numbers with a plus sign +; Default only negative are prefixed with a negative sign.");
        put(" ", "Pads the result with spaces. This is the default.");
        put("0", "Only left-pads numbers with zeros. With s specifiers this can also right-pad with zeros.");
        put("'", "Pads the result with the following character.");
        put("%", "A literal percent character.");
        put("c", "The text is interpreted as the character with that ASCII and returned as an integer.");
        put("d", "The text is interpreted as a (signed) decimal number and returned as an integer.");
        put("D", "The text is interpreted as a decimal number and returned as an integer.");
        put("e", "The text is interpreted as scientific notation (e.g. 1.2e+2). The precision specifier stands for the number of digits after the decimal point since PHP 5.2.1. In earlier versions, it was taken as number of significant digits (one less).");
        put("E", "Like the e specifier but uses uppercase letter (e.g. 1.2E+2).");
        put("f", "The text is interpreted as a floating-point number (not locale aware) and returned as a float.");
        put("i", "The text is interpreted as an integer with base detection.");
        put("n", "Returns the number of characters processed so far.");
        put("o", "The text is interpreted as an octal number and returned as an integer.");
        put("s", "The text is interpreted as a string.<br>Note: Stops reading at any whitespace character.");
        put("u", "The text is interpreted as an unsigned decimal number and returned as an integer.");
        put("x", "The text is interpreted as a hexadecimal number (with lowercase letters) and returned as an integer.");
        put("X", "The text is interpreted as a hexadecimal number (with uppercase letters) and returned as an integer.");
    }};
}
