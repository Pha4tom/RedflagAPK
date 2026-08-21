package com.google.android.material.navigation;

import android.content.Context;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.SubMenuBuilder;

/* JADX INFO: loaded from: classes.dex */
public class NavigationBarSubMenu extends SubMenuBuilder {
    public NavigationBarSubMenu(Context context, NavigationBarMenu menu, MenuItemImpl item) {
        super(context, menu, item);
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder
    public void onItemsChanged(boolean structureChanged) {
        super.onItemsChanged(structureChanged);
        ((MenuBuilder) getParentMenu()).onItemsChanged(structureChanged);
    }
}
