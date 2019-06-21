package com.myProject.bankSystem.pagination;

public class Pagination {

   private int pageId;
   private int allPages;
   private int totalItemsPerPage;
   private int allItems;
   private int[] pagesArray;

    public Pagination(int pageId, int allItems, int totalItemsPerPage) {
        this.pageId = pageId;
        this.allItems = allItems;
        this.totalItemsPerPage = totalItemsPerPage;

        calculateAllPages();
        createPagesArr();
        calculatePageId();
    }

    /**
     * calculating the pages array size
     */
    private void calculateAllPages(){
        allPages = allItems / totalItemsPerPage;

        if(allPages > 1 && ((double)allItems / totalItemsPerPage) % 2 != 0){
            allPages++;
        } else if(allPages == 1 && allItems > totalItemsPerPage)
            allPages++;
    }

    /**
     * filling pages array with ordinal numbers
     */
    private void createPagesArr(){
        pagesArray = new int[allPages];

        for (int i = 0; i < allPages; i++) {
            pagesArray[i] = i + 1;
        }
    }

    /**
     * calculating current item number in db
     */
    private void calculatePageId(){
        if(pageId > 1){
            pageId = pageId - 1;
            pageId = pageId * totalItemsPerPage + 1;
        }
    }

    public int getPageId() {
        return pageId;
    }

    public int getAllPages() {
        return allPages;
    }

    public int[] getPagesArray() {
        return pagesArray;
    }
}
