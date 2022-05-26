import React, { useEffect, useState } from "react";
import DataTable from "react-data-table-component";
import { toast } from "react-toastify";
import ChiefService from "../../services/chief.service";

const Rankings = () => {
  const [rankings, setRankings] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const columns = [
    {
      name: "Rank",
      selector: (row) => row.rank + 1,
    },
    {
      name: "Teacher",
      grow: 3,
      selector: (row) => row.teacherName,
    },
  ];

  useEffect(() => {
    ChiefService.getTeacherRankings()
      .then((data) => {
        setRankings(data.data);
      })
      .catch((err) => {
        console.log(err);
        toast.error("Getting rankings error:", err.error.message);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, []);

  return (
    <DataTable
      columns={columns}
      data={rankings}
      striped
      progressPending={isLoading}
    />
  );
};

export default Rankings;
