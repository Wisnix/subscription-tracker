export interface Subscription {
  id?: string;
  user_id?: string;
  startDate: Date;
  price: number;
  pay_interval: string;
  name: string;
  status?: string;
  free_period: number;
  reminder: boolean;
}
