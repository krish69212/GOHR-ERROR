
package org.glassfish.jersey.jaxb.internal;

import java.util.Locale;
import java.util.ResourceBundle;
import org.glassfish.jersey.internal.l10n.Localizable;
import org.glassfish.jersey.internal.l10n.LocalizableMessageFactory;
import org.glassfish.jersey.internal.l10n.LocalizableMessageFactory.ResourceBundleSupplier;
import org.glassfish.jersey.internal.l10n.Localizer;


/**
 * Defines string formatting method for each constant in the resource file
 * 
 */
public final class LocalizationMessages {

    private final static String BUNDLE_NAME = "org.glassfish.jersey.jaxb.internal.localization";
    private final static LocalizableMessageFactory MESSAGE_FACTORY = new LocalizableMessageFactory(BUNDLE_NAME, new LocalizationMessages.BundleSupplier());
    private final static Localizer LOCALIZER = new Localizer();

    public static Localizable localizableERROR_UNMARSHALLING_JAXB(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.unmarshalling.jaxb", arg0);
    }

    /**
     * Error un-marshalling JAXB object of type: {0}.
     * 
     */
    public static String ERROR_UNMARSHALLING_JAXB(Object arg0) {
        return LOCALIZER.localize(localizableERROR_UNMARSHALLING_JAXB(arg0));
    }

    public static Localizable localizableCANNOT_SET_PROPERTY(Object arg0, Object arg1, Object arg2, Object arg3) {
        return MESSAGE_FACTORY.getMessage("cannot.set.property", arg0, arg1, arg2, arg3);
    }

    /**
     * Cannot set property "{0}"="{1}" on instance of {2}, {3}.
     * 
     */
    public static String CANNOT_SET_PROPERTY(Object arg0, Object arg1, Object arg2, Object arg3) {
        return LOCALIZER.localize(localizableCANNOT_SET_PROPERTY(arg0, arg1, arg2, arg3));
    }

    public static Localizable localizableSAX_CANNOT_DISABLE_PARAMETER_ENTITY_PROCESSING_FEATURE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("sax.cannot.disable.parameter.entity.processing.feature", arg0);
    }

    /**
     * Cannot disable external parameter entity processing feature on SAX parser factory [{0}].
     * 
     */
    public static String SAX_CANNOT_DISABLE_PARAMETER_ENTITY_PROCESSING_FEATURE(Object arg0) {
        return LOCALIZER.localize(localizableSAX_CANNOT_DISABLE_PARAMETER_ENTITY_PROCESSING_FEATURE(arg0));
    }

    public static Localizable localizableERROR_READING_ENTITY_MISSING() {
        return MESSAGE_FACTORY.getMessage("error.reading.entity.missing");
    }

    /**
     * Missing entity.
     * 
     */
    public static String ERROR_READING_ENTITY_MISSING() {
        return LOCALIZER.localize(localizableERROR_READING_ENTITY_MISSING());
    }

    public static Localizable localizableSAX_CANNOT_ENABLE_DISALLOW_DOCTYPE_DECLARATION_FEATURE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("sax.cannot.enable.disallow.doctype.declaration.feature", arg0);
    }

    /**
     * Cannot enable feature that disallows doctype declaration on SAX parser factory [{0}].
     * 
     */
    public static String SAX_CANNOT_ENABLE_DISALLOW_DOCTYPE_DECLARATION_FEATURE(Object arg0) {
        return LOCALIZER.localize(localizableSAX_CANNOT_ENABLE_DISALLOW_DOCTYPE_DECLARATION_FEATURE(arg0));
    }

    public static Localizable localizableSAX_CANNOT_DISABLE_GENERAL_ENTITY_PROCESSING_FEATURE_ON_READER(Object arg0) {
        return MESSAGE_FACTORY.getMessage("sax.cannot.disable.general.entity.processing.feature.on.reader", arg0);
    }

    /**
     * Cannot disable external general entity processing feature on XML reader [{0}].
     * 
     */
    public static String SAX_CANNOT_DISABLE_GENERAL_ENTITY_PROCESSING_FEATURE_ON_READER(Object arg0) {
        return LOCALIZER.localize(localizableSAX_CANNOT_DISABLE_GENERAL_ENTITY_PROCESSING_FEATURE_ON_READER(arg0));
    }

    public static Localizable localizableNO_PARAM_CONSTRUCTOR_MISSING(Object arg0) {
        return MESSAGE_FACTORY.getMessage("no.param.constructor.missing", arg0);
    }

    /**
     * No-param constructor not found in the class [{0}].
     * 
     */
    public static String NO_PARAM_CONSTRUCTOR_MISSING(Object arg0) {
        return LOCALIZER.localize(localizableNO_PARAM_CONSTRUCTOR_MISSING(arg0));
    }

    public static Localizable localizableSAX_CANNOT_DISABLE_GENERAL_ENTITY_PROCESSING_FEATURE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("sax.cannot.disable.general.entity.processing.feature", arg0);
    }

    /**
     * Cannot disable external general entity processing feature on SAX parser factory [{0}].
     * 
     */
    public static String SAX_CANNOT_DISABLE_GENERAL_ENTITY_PROCESSING_FEATURE(Object arg0) {
        return LOCALIZER.localize(localizableSAX_CANNOT_DISABLE_GENERAL_ENTITY_PROCESSING_FEATURE(arg0));
    }

    public static Localizable localizableUNABLE_TO_SECURE_XML_TRANSFORMER_PROCESSING() {
        return MESSAGE_FACTORY.getMessage("unable.to.secure.xml.transformer.processing");
    }

    /**
     * Unable to configure secure XML processing feature on the XML transformer factory.
     * 
     */
    public static String UNABLE_TO_SECURE_XML_TRANSFORMER_PROCESSING() {
        return LOCALIZER.localize(localizableUNABLE_TO_SECURE_XML_TRANSFORMER_PROCESSING());
    }

    public static Localizable localizableSAX_XDK_NO_SECURITY_FEATURES() {
        return MESSAGE_FACTORY.getMessage("sax.xdk.no.security.features");
    }

    /**
     * Using XDK. No security features will be enabled for the SAX parser.
     * 
     */
    public static String SAX_XDK_NO_SECURITY_FEATURES() {
        return LOCALIZER.localize(localizableSAX_XDK_NO_SECURITY_FEATURES());
    }

    public static Localizable localizableSAX_CANNOT_ENABLE_SECURE_PROCESSING_FEATURE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("sax.cannot.enable.secure.processing.feature", arg0);
    }

    /**
     * JAXP feature XMLConstants.FEATURE_SECURE_PROCESSING cannot be set on a SAX parser factory [{0}].
     * 
     */
    public static String SAX_CANNOT_ENABLE_SECURE_PROCESSING_FEATURE(Object arg0) {
        return LOCALIZER.localize(localizableSAX_CANNOT_ENABLE_SECURE_PROCESSING_FEATURE(arg0));
    }

    public static Localizable localizableUNABLE_TO_ACCESS_METHODS_OF_CLASS(Object arg0) {
        return MESSAGE_FACTORY.getMessage("unable.to.access.methods.of.class", arg0);
    }

    /**
     * Unable to access the methods of the class [{0}]. Caller's class-loader hierarchy for not in the ancestor chain of the class.
     * 
     */
    public static String UNABLE_TO_ACCESS_METHODS_OF_CLASS(Object arg0) {
        return LOCALIZER.localize(localizableUNABLE_TO_ACCESS_METHODS_OF_CLASS(arg0));
    }

    public static Localizable localizableUNABLE_TO_INSTANTIATE_CLASS(Object arg0) {
        return MESSAGE_FACTORY.getMessage("unable.to.instantiate.class", arg0);
    }

    /**
     * Unable to instantiate the class [{0}].
     * 
     */
    public static String UNABLE_TO_INSTANTIATE_CLASS(Object arg0) {
        return LOCALIZER.localize(localizableUNABLE_TO_INSTANTIATE_CLASS(arg0));
    }

    public static Localizable localizableCANNOT_SET_FEATURE(Object arg0, Object arg1, Object arg2, Object arg3) {
        return MESSAGE_FACTORY.getMessage("cannot.set.feature", arg0, arg1, arg2, arg3);
    }

    /**
     * Cannot set feature "{0}"="{1}" on instance of {2}, {3}.
     * 
     */
    public static String CANNOT_SET_FEATURE(Object arg0, Object arg1, Object arg2, Object arg3) {
        return LOCALIZER.localize(localizableCANNOT_SET_FEATURE(arg0, arg1, arg2, arg3));
    }

    private static class BundleSupplier
        implements ResourceBundleSupplier
    {


        public ResourceBundle getResourceBundle(Locale locale) {
            return ResourceBundle.getBundle(BUNDLE_NAME, locale);
        }

    }

}
