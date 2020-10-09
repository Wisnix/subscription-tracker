import { Component, OnInit } from '@angular/core';
import * as Chart from 'chart.js';
import * as dayjs from 'dayjs';
import { Subscription } from '../subscription/subscription.model';
import { SubscriptionService } from '../subscription/subscription.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  canvas: any;
  ctx: any;

  constructor(private subscriptionService: SubscriptionService) {}

  ngOnInit(): void {
    this.canvas = document.getElementById('myChart');
    this.ctx = this.canvas.getContext('2d');    
    this.subscriptionService.getSubscriptions().subscribe((subscriptions) => {
      const expenditure=this.calculateTotalMonthlyExpenditure(subscriptions);
      this.initChart(expenditure);
      this.getNextPayments(subscriptions);
    });
  }

  private initChart(data:Array<number>){
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
            label: 'Total subscriptions cost in £',
            backgroundColor: 'rgb(255, 99, 132)',
            borderColor: 'rgb(255, 99, 132)',
            data: data,
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

  private calculateTotalMonthlyExpenditure(subscriptions: Subscription[]):Array<number> {
    let expenditure = new Array<number>(12);
    const now = dayjs();
    subscriptions.forEach((subscription) => {
      if(subscription.status==='disabled')return subscription;
      let date = dayjs(subscription.startDate).add(
        subscription.freePeriod,
        'd'
      );
      const type: dayjs.OpUnitType = this.subscriptionService.getOpUnitType(
        subscription.payInterval
      );
      while (date.isBefore(now)) {
        const currentValue=expenditure[date.month()]||0;
        if(date.year()==now.year())
        expenditure[date.month()] =  currentValue+subscription.price;
        date=date.add(1, type);
      }
    });
    return expenditure;
  }

  private getNextPayments(subscriptions:Subscription[]){
    let nextPayments=[];
    subscriptions.forEach(subscription=>{
      const now = dayjs();
      if(subscription.status==='disabled')return subscription;
      let nextPayment = dayjs(subscription.startDate).add(subscription.freePeriod,'d' );
      const type: dayjs.OpUnitType = this.subscriptionService.getOpUnitType(subscription.payInterval);
      while (nextPayment.isBefore(now)) {
        nextPayment=nextPayment.add(1, type);
      }
      nextPayments.push(nextPayment);
    })
    nextPayments.forEach(p=>console.log(p.format('ddd DD MMM YYYY')));
  }
}
