import * as React from "react";
import { DataTable } from "react-native-paper";
import { useState, useEffect } from "react";

const Table = ({ items }) => {
    const [page, setPage] = useState(0);

    const [numberOfItemsPerPageList, setNumberOfItems] = useState([2, 3, 4]);
    const [itemsPerPage, onItemsPerPageChange] = useState(
        numberOfItemsPerPageList[0]
    );

    const from = page * itemsPerPage;
    const to = Math.min((page + 1) * itemsPerPage, items.length);

    useEffect(() => {
        setPage(0);
        setNumberOfItems(
            items.map((_, idx) => {
                return idx + 1;
            })
        );
    }, [itemsPerPage, items]);

    return (
        <DataTable>
            <DataTable.Header>
                <DataTable.Title>Numéro</DataTable.Title>
                <DataTable.Title>Date</DataTable.Title>
                <DataTable.Title numeric>fréquence cardiaque</DataTable.Title>
            </DataTable.Header>

            {items.slice(from, to).map((item) => (
                <DataTable.Row key={item.key}>
                    <DataTable.Cell>{item.key}</DataTable.Cell>
                    <DataTable.Cell>{item.date}</DataTable.Cell>
                    <DataTable.Cell numeric>{item.heartRate}</DataTable.Cell>
                </DataTable.Row>
            ))}

            <DataTable.Pagination
                page={page}
                numberOfPages={Math.ceil(items.length / itemsPerPage)}
                onPageChange={(page) => setPage(page)}
                label={`${from + 1}-${to} of ${items.length}`}
                numberOfItemsPerPageList={numberOfItemsPerPageList}
                numberOfItemsPerPage={itemsPerPage}
                onItemsPerPageChange={onItemsPerPageChange}
                showFastPaginationControls
                selectPageDropdownLabel={"Rows per page"}
            />
        </DataTable>
    );
};

export default Table;
