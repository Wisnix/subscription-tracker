export interface Subscription {
  id?: string;
  userId?: string;
  startDate: Date;
  price: number;
  payInterval: string;
  name: string;
  status?: string;
  freePeriod: number;
  reminder: boolean;
  nextPayment: string;
}
