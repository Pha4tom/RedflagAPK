package com.google.android.material.navigation;

import android.view.MenuItem;
import android.view.SubMenu;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NavigationBarMenuBuilder {
    private final MenuBuilder menuBuilder;
    private int contentItemCount = 0;
    private int visibleContentItemCount = 0;
    private int visibleMainItemCount = 0;
    private final List<MenuItem> items = new ArrayList();

    NavigationBarMenuBuilder(MenuBuilder menuBuilder) {
        this.menuBuilder = menuBuilder;
        refreshItems();
    }

    public int size() {
        return this.items.size();
    }

    public int getContentItemCount() {
        return this.contentItemCount;
    }

    public int getVisibleContentItemCount() {
        return this.visibleContentItemCount;
    }

    public int getVisibleMainContentItemCount() {
        return this.visibleMainItemCount;
    }

    public MenuItem getItemAt(int i) {
        return this.items.get(i);
    }

    public boolean performItemAction(MenuItem item, MenuPresenter presenter, int flags) {
        return this.menuBuilder.performItemAction(item, presenter, flags);
    }

    public void refreshItems() {
        this.items.clear();
        this.contentItemCount = 0;
        this.visibleContentItemCount = 0;
        this.visibleMainItemCount = 0;
        for (int i = 0; i < this.menuBuilder.size(); i++) {
            MenuItem item = this.menuBuilder.getItem(i);
            if (item.hasSubMenu()) {
                if (!this.items.isEmpty() && !(this.items.get(this.items.size() - 1) instanceof DividerMenuItem) && item.isVisible()) {
                    this.items.add(new DividerMenuItem());
                }
                this.items.add(item);
                SubMenu subMenu = item.getSubMenu();
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem submenuItem = subMenu.getItem(j);
                    if (!item.isVisible()) {
                        submenuItem.setVisible(false);
                    }
                    this.items.add(submenuItem);
                    this.contentItemCount++;
                    if (submenuItem.isVisible()) {
                        this.visibleContentItemCount++;
                    }
                }
                this.items.add(new DividerMenuItem());
            } else {
                this.items.add(item);
                this.contentItemCount++;
                if (item.isVisible()) {
                    this.visibleContentItemCount++;
                    this.visibleMainItemCount++;
                }
            }
        }
        if (!this.items.isEmpty() && (this.items.get(this.items.size() - 1) instanceof DividerMenuItem)) {
            this.items.remove(this.items.size() - 1);
        }
    }
}
