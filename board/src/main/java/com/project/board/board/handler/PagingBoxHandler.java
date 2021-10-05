package com.project.board.board.handler;

public class PagingBoxHandler {

    private PagingHandler pagingHandler;

    private int totalCount;

    private int startPage;
    private int endPage;

    private int lastPage;

    private int totalEndPage;

    private int displayPageNum = 5;


    public void buildPagingBox(PagingHandler pagingHandler, int totalCount){
        this.pagingHandler = pagingHandler;
        this.totalCount = totalCount;
        lastPage = (int) (Math.ceil(totalCount / (double) pagingHandler.getArticleCntInAPage()));

        overRequestLastPageErrorHandler(pagingHandler);

        this.endPage = (int) (Math.ceil(pagingHandler.getCurPage() / (double) displayPageNum) * displayPageNum);

        if (endPage > lastPage) {
            endPage = lastPage;
        }

        if(pagingHandler.getCurPage()%displayPageNum == 0){
            this.startPage = ((pagingHandler.getCurPage() / displayPageNum)-1) * displayPageNum+1;
        }else {
            this.startPage = (pagingHandler.getCurPage() / displayPageNum) * displayPageNum +1;
        }
        if (startPage <= 0) startPage = 1;
        this.totalEndPage = lastPage - (lastPage%displayPageNum);

    }

    private void overRequestLastPageErrorHandler(PagingHandler pagingHandler) {
        if(pagingHandler.getCurPage()>lastPage){
            pagingHandler.setCurPage(lastPage);

        }
        if(pagingHandler.getCurPage()<=0){
            pagingHandler.setCurPage(1);
        }
    }



    //Get,Set

    public PagingHandler getPagingIndex() {
        return pagingHandler;
    }

    public void setPagingIndex(PagingHandler pagingHandler) {
        this.pagingHandler = pagingHandler;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getStartPage() {
        return startPage;
    }
    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }
    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getTotalEndPage() {
        return totalEndPage;
    }
    public void setTotalEndPage(int totalEndPage) {
        this.totalEndPage = totalEndPage;
    }


    public int getDisplayPageNum() {
        return displayPageNum;
    }
    public void setDisplayPageNum(int displayPageNum) {
        this.displayPageNum = displayPageNum;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }


}
