import React from "react";
import css from "./Pagination.module.css";

const Pagination = (props) => {
    const limit = 5;
    return (
        <>
            {_generatePagination(props, limit)}
        </>
    );
}

const _generatePagination = (props, limit) => {
    const pagination = _calculatePages(props, limit);
    pagination.push("next");
    pagination.unshift("prev");
    return pagination.map(element => {
        let pageNumber = element;
        if (element === "next") {
            pageNumber = props.pageNumber + 1;
        }
        if (element === "prev") {
            pageNumber = props.pageNumber - 1;
        }
        if (pageNumber > props.totalPages || pageNumber <= 0) {
            return null;
        }
        return (
            <button className={element === props.pageNumber ? css.currentPage : ""}
                       onClick={() => props.onPageChanged(pageNumber)}>
            {element}
            </button>
        );
    });
}

const _calculatePages = (props, limit) => {
    let pages = [props.pageNumber];
    pages = pages.concat(_calculateRight(props, limit));
    limit = limit - pages.length;
    return _calculateLeft(props, limit).concat(pages);
}

const _calculateRight = (props, limit) => {
    const result = [];
    const mid = Math.floor(limit / 2);
    let counter = props.pageNumber <= mid ? limit - props.pageNumber : mid;
    let page = props.pageNumber + 1;
    while (counter > 0 && page <= props.totalPages) {
        result.push(page++);
        counter--;
    }
    return result;
}

const _calculateLeft = (props, limit) => {
    const result = [];
    let counter = limit;
    let page = props.pageNumber - 1;
    while (counter > 0 && page > 0) {
        result.unshift(page--);
        counter--;
    }
    return result;
}

export default Pagination;