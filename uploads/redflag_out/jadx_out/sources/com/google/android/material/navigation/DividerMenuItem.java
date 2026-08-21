package com.google.android.material.navigation;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class DividerMenuItem implements MenuItem {
    DividerMenuItem() {
    }

    @Override // android.view.MenuItem
    public boolean collapseActionView() {
        return false;
    }

    @Override // android.view.MenuItem
    public boolean expandActionView() {
        return false;
    }

    @Override // android.view.MenuItem
    public ActionProvider getActionProvider() {
        return null;
    }

    @Override // android.view.MenuItem
    public View getActionView() {
        return null;
    }

    @Override // android.view.MenuItem
    public char getAlphabeticShortcut() {
        return (char) 0;
    }

    @Override // android.view.MenuItem
    public int getGroupId() {
        return 0;
    }

    @Override // android.view.MenuItem
    public Drawable getIcon() {
        return null;
    }

    @Override // android.view.MenuItem
    public Intent getIntent() {
        return null;
    }

    @Override // android.view.MenuItem
    public int getItemId() {
        return 0;
    }

    @Override // android.view.MenuItem
    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
    }

    @Override // android.view.MenuItem
    public char getNumericShortcut() {
        return (char) 0;
    }

    @Override // android.view.MenuItem
    public int getOrder() {
        return 0;
    }

    @Override // android.view.MenuItem
    public SubMenu getSubMenu() {
        return null;
    }

    @Override // android.view.MenuItem
    public CharSequence getTitle() {
        return null;
    }

    @Override // android.view.MenuItem
    public CharSequence getTitleCondensed() {
        return null;
    }

    @Override // android.view.MenuItem
    public boolean hasSubMenu() {
        return false;
    }

    @Override // android.view.MenuItem
    public boolean isActionViewExpanded() {
        return false;
    }

    @Override // android.view.MenuItem
    public boolean isCheckable() {
        return false;
    }

    @Override // android.view.MenuItem
    public boolean isChecked() {
        return false;
    }

    @Override // android.view.MenuItem
    public boolean isEnabled() {
        return false;
    }

    @Override // android.view.MenuItem
    public boolean isVisible() {
        return false;
    }

    @Override // android.view.MenuItem
    public MenuItem setActionProvider(ActionProvider actionProvider) {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setActionView(View view) {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setActionView(int resId) {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setAlphabeticShortcut(char alphaChar) {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setCheckable(boolean checkable) {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setChecked(boolean checked) {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setEnabled(boolean enabled) {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setIcon(Drawable icon) {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setIcon(int iconRes) {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setIntent(Intent intent) {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setNumericShortcut(char numericChar) {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener listener) {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener menuItemClickListener) {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setShortcut(char numericChar, char alphaChar) {
        return null;
    }

    @Override // android.view.MenuItem
    public void setShowAsAction(int actionEnum) {
    }

    @Override // android.view.MenuItem
    public MenuItem setShowAsActionFlags(int actionEnum) {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitle(int title) {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitle(CharSequence title) {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitleCondensed(CharSequence title) {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setVisible(boolean visible) {
        return null;
    }
}
