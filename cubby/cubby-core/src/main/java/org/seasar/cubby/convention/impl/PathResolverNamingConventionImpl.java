package org.seasar.cubby.convention.impl;

import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.convention.impl.NamingConventionImpl;

public class PathResolverNamingConventionImpl extends NamingConventionImpl {

	private NamingConvention namingConvention;

	public void setNamingConvention(final NamingConvention namingConvention) {
		this.namingConvention = namingConvention;
	}

	@Override
	public boolean isTargetClassName(String className, String suffix) {
		if (!this.getActionSuffix().equals(suffix)) {
			return false;
		}
		return namingConvention.isTargetClassName(className, suffix);
	}

	public boolean isTargetClassName(String s) {
		if (!namingConvention.isTargetClassName(s)) {
			return false;
		}

		return s.endsWith(namingConvention.getActionSuffix());
	}

	public String adjustViewRootPath() {
		return namingConvention.adjustViewRootPath();
	}
	public String fromActionNameToPageName(String s) {
		return namingConvention.fromActionNameToPageName(s);
	}
	public String fromActionNameToPath(String s) {
		return namingConvention.fromActionNameToPath(s);
	}
	public String fromClassNameToComponentName(String s) {
		return namingConvention.fromClassNameToComponentName(s);
	}
	public String fromClassNameToShortComponentName(String s) {
		return namingConvention.fromClassNameToShortComponentName(s);
	}
	public String fromClassNameToSuffix(String s) {
		return namingConvention.fromClassNameToSuffix(s);
	}
	public Class fromComponentNameToClass(String s) {
		return namingConvention.fromComponentNameToClass(s);
	}
	public String fromComponentNameToPartOfClassName(String s) {
		return namingConvention.fromComponentNameToPartOfClassName(s);
	}
	public String fromComponentNameToSuffix(String s) {
		return namingConvention.fromComponentNameToSuffix(s);
	}
	public String fromPageClassToPath(Class class1) {
		return namingConvention.fromPageClassToPath(class1);
	}
	public String fromPageNameToPath(String s) {
		return namingConvention.fromPageNameToPath(s);
	}
	public String fromPathToActionName(String s) {
		return namingConvention.fromPathToActionName(s);
	}
	public String fromPathToPageName(String s) {
		return namingConvention.fromPathToPageName(s);
	}
	public String fromSuffixToPackageName(String s) {
		return namingConvention.fromSuffixToPackageName(s);
	}
	public String getActionSuffix() {
		return namingConvention.getActionSuffix();
	}
	public String getConnectorPackageName() {
		return namingConvention.getConnectorPackageName();
	}
	public String getConnectorSuffix() {
		return namingConvention.getConnectorSuffix();
	}
	public String getConverterPackageName() {
		return namingConvention.getConverterPackageName();
	}
	public String getConverterSuffix() {
		return namingConvention.getConverterSuffix();
	}
	public String getDaoPackageName() {
		return namingConvention.getDaoPackageName();
	}
	public String getDaoSuffix() {
		return namingConvention.getDaoSuffix();
	}
	public String getDtoPackageName() {
		return namingConvention.getDtoPackageName();
	}
	public String getDtoSuffix() {
		return namingConvention.getDtoSuffix();
	}
	public String getDxoPackageName() {
		return namingConvention.getDxoPackageName();
	}
	public String getDxoSuffix() {
		return namingConvention.getDxoSuffix();
	}
	public String getEntityPackageName() {
		return namingConvention.getEntityPackageName();
	}
	public String getHelperPackageName() {
		return namingConvention.getHelperPackageName();
	}
	public String getHelperSuffix() {
		return namingConvention.getHelperSuffix();
	}
	public String[] getIgnorePackageNames() {
		return namingConvention.getIgnorePackageNames();
	}
	public String getImplementationPackageName() {
		return namingConvention.getImplementationPackageName();
	}
	public String getImplementationSuffix() {
		return namingConvention.getImplementationSuffix();
	}
	public String getInterceptorPackageName() {
		return namingConvention.getInterceptorPackageName();
	}
	public String getInterceptorSuffix() {
		return namingConvention.getInterceptorSuffix();
	}
	public String getLogicPackageName() {
		return namingConvention.getLogicPackageName();
	}
	public String getLogicSuffix() {
		return namingConvention.getLogicSuffix();
	}
	public String getPageSuffix() {
		return namingConvention.getPageSuffix();
	}
	public String[] getRootPackageNames() {
		return namingConvention.getRootPackageNames();
	}
	public String getServicePackageName() {
		return namingConvention.getServicePackageName();
	}
	public String getServiceSuffix() {
		return namingConvention.getServiceSuffix();
	}
	public String getSubApplicationRootPackageName() {
		return namingConvention.getSubApplicationRootPackageName();
	}
	public String getValidatorPackageName() {
		return namingConvention.getValidatorPackageName();
	}
	public String getValidatorSuffix() {
		return namingConvention.getValidatorSuffix();
	}
	public String getViewExtension() {
		return namingConvention.getViewExtension();
	}
	public String getViewRootPath() {
		return namingConvention.getViewRootPath();
	}
	public boolean isIgnoreClassName(String s) {
		return namingConvention.isIgnoreClassName(s);
	}
	public boolean isSkipClass(Class class1) {
		return namingConvention.isSkipClass(class1);
	}
	public boolean isValidViewRootPath(String s) {
		return namingConvention.isValidViewRootPath(s);
	}
	public Class toCompleteClass(Class class1) {
		return namingConvention.toCompleteClass(class1);
	}
	public String toImplementationClassName(String s) {
		return namingConvention.toImplementationClassName(s);
	}
	public String toInterfaceClassName(String s) {
		return namingConvention.toInterfaceClassName(s);
	}

}
