import { Component, OnInit } from '@angular/core';
import * as Chart from 'chart.js';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  canvas: any;
  ctx: any;

  constructor() {}

  ngOnInit(): void {
    this.canvas = document.getElementById('myChart');
    this.ctx = this.canvas.getContext('2d');
    const myChart = new Chart(this.ctx, {
      type: 'bar',
      data: {
        labels: [
          'January',
          'February',
          'March',
          'April',
          'May',
          'June',
          'July',
          'August',
          'September',
          'October',
          'November',
          'December',
        ],
        datasets: [
          {
            label: 'Total subscription cost',
            backgroundColor: 'rgb(255, 99, 132)',
            borderColor: 'rgb(255, 99, 132)',
            data: [0, 10, 5, 2, 20, 30, 45],
          },
        ],
      },
      options: {
        legend: {
          display: false,
        },
        responsive: false,
        title: {
          display: true,
          text: 'Expenditure by month',
        },
      },
    });
  }

  private calculateMonthlyExpenditure(subscriptions: Subscription[]) {}
}
