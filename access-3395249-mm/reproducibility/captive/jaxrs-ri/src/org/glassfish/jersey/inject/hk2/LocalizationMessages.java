
package org.glassfish.jersey.inject.hk2;

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

    private final static String BUNDLE_NAME = "org.glassfish.jersey.inject.hk2.localization";
    private final static LocalizableMessageFactory MESSAGE_FACTORY = new LocalizableMessageFactory(BUNDLE_NAME, new LocalizationMessages.BundleSupplier());
    private final static Localizer LOCALIZER = new Localizer();

    public static Localizable localizableHK_2_UNKNOWN_ERROR(Object arg0) {
        return MESSAGE_FACTORY.getMessage("hk2.unknown.error", arg0);
    }

    /**
     * Unknown HK2 failure detected:
     * {0}
     * 
     */
    public static String HK_2_UNKNOWN_ERROR(Object arg0) {
        return LOCALIZER.localize(localizableHK_2_UNKNOWN_ERROR(arg0));
    }

    public static Localizable localizableHK_2_UNKNOWN_PARENT_INJECTION_MANAGER(Object arg0) {
        return MESSAGE_FACTORY.getMessage("hk2.unknown.parent.injection.manager", arg0);
    }

    /**
     * Unknown parent of InjectionManager, ServiceLocator should be used instead of: {0}.
     * 
     */
    public static String HK_2_UNKNOWN_PARENT_INJECTION_MANAGER(Object arg0) {
        return LOCALIZER.localize(localizableHK_2_UNKNOWN_PARENT_INJECTION_MANAGER(arg0));
    }

    public static Localizable localizableHK_2_FAILURE_OUTSIDE_ERROR_SCOPE() {
        return MESSAGE_FACTORY.getMessage("hk2.failure.outside.error.scope");
    }

    /**
     * HK2 failure has been detected in a code that does not run in an active Jersey Error scope.
     * 
     */
    public static String HK_2_FAILURE_OUTSIDE_ERROR_SCOPE() {
        return LOCALIZER.localize(localizableHK_2_FAILURE_OUTSIDE_ERROR_SCOPE());
    }

    public static Localizable localizableHK_2_PROVIDER_NOT_REGISTRABLE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("hk2.provider.not.registrable", arg0);
    }

    /**
     * Provider registered to Hk2InjectionManager cannot be process because of incompatible type: {0}.
     * 
     */
    public static String HK_2_PROVIDER_NOT_REGISTRABLE(Object arg0) {
        return LOCALIZER.localize(localizableHK_2_PROVIDER_NOT_REGISTRABLE(arg0));
    }

    public static Localizable localizableHK_2_CLEARING_CACHE(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("hk2.clearing.cache", arg0, arg1);
    }

    /**
     * Clearing Jersey HK2 caches. Service cache size: {0}, reflection cache size: {1}.
     * 
     */
    public static String HK_2_CLEARING_CACHE(Object arg0, Object arg1) {
        return LOCALIZER.localize(localizableHK_2_CLEARING_CACHE(arg0, arg1));
    }

    public static Localizable localizableHK_2_REIFICATION_ERROR(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("hk2.reification.error", arg0, arg1);
    }

    /**
     * HK2 service reification failed for [{0}] with an exception:
     * {1}
     * 
     */
    public static String HK_2_REIFICATION_ERROR(Object arg0, Object arg1) {
        return LOCALIZER.localize(localizableHK_2_REIFICATION_ERROR(arg0, arg1));
    }

    private static class BundleSupplier
        implements ResourceBundleSupplier
    {


        public ResourceBundle getResourceBundle(Locale locale) {
            return ResourceBundle.getBundle(BUNDLE_NAME, locale);
        }

    }

}
