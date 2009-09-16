/*
 * Copyright 2004 JavaFree.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.javabb.infra;

import java.util.AbstractList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Ronald Tetsuo Miura
 * @since 16/02/2005
 */
public class PagedList extends AbstractList {
    private final List _pageItems;
    private final int _pageNumber;
    private final int _itemsPerPage;
    private final int _totalItemCount;

    /**
     * @param pageItems
     * @param pageNumber
     * @param itemsPerPage
     * @param totalItemCount
     */
    public PagedList(List pageItems, int pageNumber, int itemsPerPage, int totalItemCount) {
        this._pageItems = pageItems;
        this._pageNumber = pageNumber;
        this._itemsPerPage = itemsPerPage;
        this._totalItemCount = totalItemCount;
    }

    /**
     * @see java.util.AbstractList#get(int)
     */
    public Object get(int index) {
        return this._pageItems.get(index);
    }

    /**
     * @see java.util.AbstractCollection#size()
     */
    public int size() {
        return this._pageItems.size();
    }

    /**
     * @return the list of items for this page
     */
    public List getPageItems() {
        return _pageItems;
    }

    /**
     * @return number of the current page
     */
    public int getPageNumber() {
        return _pageNumber;
    }

    /**
     * @return the number of items per page
     */
    private int getItemsPerPage() {
        return _itemsPerPage;
    }

    /**
     * @return total count of items
     */
    public int getTotalItemCount() {
        return _totalItemCount;
    }

    /**
     * @return total count of pages
     */
    public int getTotalPageCount() {
        return (int) Math.ceil((double) getTotalItemCount() / getItemsPerPage());
    }

    /**
     * @return true if this is the first page
     */
    public boolean isFirstPage() {
        return isFirstPage(getPageNumber());
    }

    /**
     * @return true if this is the last page
     */
    public boolean isLastPage() {
        return isLastPage(getPageNumber());
    }

    /**
     * @param page
     * @return true if the page is the first page
     */
    public boolean isFirstPage(int page) {
        return page <= 1;
    }

    /**
     * @param page
     * @return true if the page is the last page
     */
    public boolean isLastPage(int page) {
        return page >= getTotalPageCount();
    }

    /**
     * @see java.util.AbstractCollection#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append(getPageItems())
            .append(getPageNumber())
            .append(getItemsPerPage())
            .append(getTotalItemCount())
            .toString();
    }
}
