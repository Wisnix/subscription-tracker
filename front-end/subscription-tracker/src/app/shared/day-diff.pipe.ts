import { Pipe, PipeTransform } from '@angular/core';
import * as dayjs from 'dayjs';

@Pipe({
  name: 'dayDiff',
})
export class DayDiffPipe implements PipeTransform {
  transform(startDate: string, freePeriod: number): number {
    return dayjs(startDate).add(freePeriod, 'day').diff(dayjs(), 'day');
  }
}
